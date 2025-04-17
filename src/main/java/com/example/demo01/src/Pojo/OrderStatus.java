package com.example.demo01.src.Pojo;

public  enum OrderStatus {
    NEW{
        @Override
        public String defaultdescription (){
            return "Order was placed by the customer.";
        }
    },
    PACKAGED{
        @Override
        public String defaultdescription (){
            return "Products were packaged";
        }
    },
    CANCELED{
        @Override
        public String defaultdescription (){
            return "Order was rejected";
        }
    }
    ,DELIVERED{
        @Override
        public String defaultdescription (){
            return "Customer received the package";
        }
    }
    ,PROCESSING{
        @Override
        public String defaultdescription (){
            return "Order is being processing";
        }
    }
    ,PAID{
        @Override
        public String defaultdescription (){
            return "Order was paid by the customer.";
        }
    }

    ,SHIPPING{
        @Override
        public String defaultdescription (){
            return "Shipper is delivering the package";
        }
    }
    ,REFUNDED{
        @Override
        public String defaultdescription (){
            return "Products were refunded";
        }
    },
    RETURNED{
        @Override
        public String defaultdescription (){
            return "Products were returned";
        }
    }
    ,PICKED{
        @Override
        public String defaultdescription (){
            return "Shipper picked the package";
        }

    }
    ,REQUEST_RETURNED{
        @Override
        public String defaultdescription (){
            return "Customer sent request to return purchase.";
        }

    };

    public abstract String defaultdescription();

}
