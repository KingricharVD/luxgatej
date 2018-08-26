package io.luxcore.dto.rs;


import com.google.api.client.util.Key;

public class Asset {

    /*
        {
        "coin": "LUX",
        "installed": true,
        "height": 377459,
        "balance": 0,
        "LUXvalue": 0,
        "status": "active",
        "type": "PoS",
        "smartaddress": "LZqosp1H7SXkzvTsVCwyYb6R1za8zySDbP",
        "rpc": "127.0.0.1:9888",
        "pubtype": 48,
        "p2shtype": 63,
        "wiftype": 155,
        "txfee": 10000,
        "zcredits": 0,
        "zdebits": {
            "swaps": [],
            "pendingswaps": 0
        }
    },
     */

    @Key("coin")
    private String coin;
    @Key("installed")
    private Boolean isInstalled;
    @Key("height")
    private Long height;
    @Key("balance")
    private Long balance;
    @Key("LUXValue")
    private Long LUXValue;
    @Key("status")
    private String status;
    @Key("type")
    private String type;
    @Key("smartaddress")
    private String startAddress;
    @Key("rpc")
    private String rpc;
    @Key("pubtype")
    private Integer pubType;
    @Key("p2shtype")
    private Integer p2shtype;
    @Key("wiftype")
    private Integer WIFtype;
    @Key("txfee")
    private Long txFee;
    @Key("zcredits")
    private Long zCredits;

    // TODO add zdepbits


    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Boolean getInstalled() {
        return isInstalled;
    }

    public void setInstalled(Boolean installed) {
        isInstalled = installed;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getLUXValue() {
        return LUXValue;
    }

    public void setLUXValue(Long LUXValue) {
        this.LUXValue = LUXValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getRpc() {
        return rpc;
    }

    public void setRpc(String rpc) {
        this.rpc = rpc;
    }

    public Integer getPubType() {
        return pubType;
    }

    public void setPubType(Integer pubType) {
        this.pubType = pubType;
    }

    public Integer getP2shtype() {
        return p2shtype;
    }

    public void setP2shtype(Integer p2shtype) {
        this.p2shtype = p2shtype;
    }

    public Integer getWIFtype() {
        return WIFtype;
    }

    public void setWIFtype(Integer WIFtype) {
        this.WIFtype = WIFtype;
    }

    public Long getTxFee() {
        return txFee;
    }

    public void setTxFee(Long txFee) {
        this.txFee = txFee;
    }

    public Long getzCredits() {
        return zCredits;
    }

    public void setzCredits(Long zCredits) {
        this.zCredits = zCredits;
    }



    @Override
    public String toString() {
        return "Asset{" +
                "coin='" + coin + '\'' +
                ", isInstalled=" + isInstalled +
                ", height=" + height +
                ", balance=" + balance +
                ", LUXValue=" + LUXValue +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", startAddress='" + startAddress + '\'' +
                ", rpc='" + rpc + '\'' +
                ", pubType=" + pubType +
                ", p2shtype=" + p2shtype +
                ", WIFtype=" + WIFtype +
                ", txFee=" + txFee +
                ", zCredits=" + zCredits +
                '}';
    }
}
