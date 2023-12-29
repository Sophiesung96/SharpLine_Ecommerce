package com.example.demo01.src.Pojo;

public  enum OrderStatus {
    NEW{
        @Override
        public String defaultdscription (){
            return "Order was placed by the customer.";
        }
    },
    PACKAGED{
        @Override
        public String defaultdscription (){
            return "Products were packaged";
        }
    },
    CANCELED{
        @Override
        public String defaultdscription (){
            return "Order was rejected";
        }
    }
    ,DELIVERED{
        @Override
        public String defaultdscription (){
            return "Customer received the package";
        }
    }
    ,PROCESSING{
        @Override
        public String defaultdscription (){
            return "Order is being processing";
        }
    }
    ,PAID{
        @Override
        public String defaultdscription (){
            return "Order was paid by the customer.";
        }
    }

    ,SHIPPING{
        @Override
        public String defaultdscription (){
            return "Shipper is delivering the package";
        }
    }
    ,REFUNDED{
        @Override
        public String defaultdscription (){
            return "Products were refunded";
        }
    },
    RETURNED{
        @Override
        public String defaultdscription (){
            return "Products were returned";
        }
    }
    ,PICKED{
        @Override
        public String defaultdscription (){
            return "Shipper picked the package";
        }

    };

    public abstract String defaultdscription();
}
