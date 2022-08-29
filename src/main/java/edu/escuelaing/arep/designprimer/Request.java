package edu.escuelaing.arep.designprimer;

public class Request {
    private String stockName;

    private String time;

    private String provider;


    private Request(Builder builder) {
        stockName = builder.stockName;
        time = builder.time;
        provider = builder.provider;
    }

    public String getStockName() {
        return stockName;
    }

    public String getTime() {
        return time;
    }

    public String getProvider() {
        return provider;
    }

    public static Builder newBuilder(){

        return new Builder();
    }

    public static Builder newBuilder(Request copy){

        Builder builder = new Builder();
        builder.stockName = copy.getStockName();
        builder.time = copy.getTime();
        builder.provider = copy.provider;
        return builder;

    }

    public static final class Builder {
        private String stockName;
        private String time;
        private String provider;

        public Builder() {
        }

        public Builder withStockName(String stockName){
            this.stockName = stockName;
            return this;
        }

        public Builder withTime(String time){
            this.time = time;
            return this;
        }

        public Builder withProvider(String provider){
            this.provider = provider;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
