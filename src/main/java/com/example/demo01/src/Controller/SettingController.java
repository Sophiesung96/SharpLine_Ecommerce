package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.Utils.FileUploadUtil;
import com.example.demo01.src.Pojo.AmazonS3Util;
import com.example.demo01.src.Pojo.Currency;
import com.example.demo01.src.Pojo.GeneralSettingBag;
import com.example.demo01.src.Pojo.Setting;
import com.example.demo01.src.Service.CurrencyService;
import com.example.demo01.src.Service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class SettingController {

    @Autowired
    SettingService settingService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    AmazonS3Util amazonS3Util;

    @GetMapping("/settings")
    public String listAll(Model model){
      List<Setting> list=settingService.listAllSettings();
        List<Currency>clist=currencyService.findAllOrderByNameAsc();

      for(Setting s:list){
          model.addAttribute(s.getKey(),s.getValue());
      }
      model.addAttribute("clist",clist);
      return "Settings";
    }

    @PostMapping("/settings/save_general")
    public String saveGeneralSettings(@RequestParam ("fileImage")MultipartFile multipartFile, HttpServletRequest request, RedirectAttributes rs) throws IOException {
        List<Setting> settingList=settingService.getGeneralSetting();
        GeneralSettingBag generalSettingBag=new GeneralSettingBag(settingList);
        saveSiteLogo(multipartFile, settingList,generalSettingBag);
        saveCurrencySymbol(request,settingList,generalSettingBag);
        updateSettingValuesForm(request,generalSettingBag);
        rs.addFlashAttribute("message","General settings have been saved");
        return "redirect:/settings";
    }

    private void saveSiteLogo(MultipartFile multipartFile,
                              List<Setting> settingList,
                              GeneralSettingBag generalSettingBag) throws IOException {

        if (multipartFile == null || multipartFile.isEmpty()) {
            return;
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename())
                .replaceAll("\\s+", "_");

        String uploadDir = "site-logo";
        String value = uploadDir + "/" + fileName;

        generalSettingBag.updateSiteLogo(value);

        amazonS3Util.removeFolder(uploadDir);

        try (var is = multipartFile.getInputStream()) {
            amazonS3Util.uploadFile(uploadDir, fileName, is, multipartFile.getSize());
        }
    }


    private void saveCurrencySymbol(HttpServletRequest request,List<Setting> settingList,GeneralSettingBag generalSettingBag){
       Integer id=Integer.parseInt( request.getParameter("CURRENCY_ID"));
        Optional<Currency> findByResult=currencyService.findById(id);
        if(findByResult.isPresent())
            findByResult.get();
            String symbol= findByResult.get().getSymbol();
            generalSettingBag.updateCurrencySymbol(symbol);
           ;
        }


    private void updateSettingValuesForm(HttpServletRequest request,GeneralSettingBag settingList){
       for(Setting setting:settingList.getlist()){
           String value=request.getParameter(setting.getKey());
           System.out.println(value);
           if(value!=null){
               setting.setValue(value);
           }
       }
        settingService.saveAll(settingList.getlist());

    }

    private void updateSettingValuesForMailServer(HttpServletRequest request,List<Setting> settingList){
        for(Setting setting:settingList){
            String value=request.getParameter(setting.getKey());
            System.out.println(value);
            if(value!=null){
                setting.setValue(value);
            }
        }
        settingService.saveAll(settingList);

    }
    private void updateSettingValuesForMailTemplate(HttpServletRequest request,List<Setting> settingList){
        for(Setting setting:settingList){
            String value=request.getParameter(setting.getKey());
            System.out.println(value);
            if(value!=null){
                setting.setValue(value);
            }
        }
        settingService.saveAll(settingList);

    }

    @PostMapping("/settings/save_mail_server")
    public String saveMailSettingServer(HttpServletRequest httpServletRequest,RedirectAttributes ra){
       List<Setting> list=settingService.getMailServerSetting();
        updateSettingValuesForMailServer(httpServletRequest,list);
       ra.addFlashAttribute("message","Mail Server settings have been saved");

        return "redirect:/settings";
    }

    @PostMapping("/settings/save_mail_templates")
    public String saveMailSettingTemplate(HttpServletRequest httpServletRequest,RedirectAttributes ra){
        List<Setting> list=settingService.getMailTEMPLATESetting();
        updateSettingValuesForMailTemplate(httpServletRequest,list);
        ra.addFlashAttribute("message","Mail Template has been saved");
        return "redirect:/settings";
    }



}
