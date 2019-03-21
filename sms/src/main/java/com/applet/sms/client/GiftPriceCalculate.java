package com.applet.sms.client;

import java.math.BigDecimal;

public class GiftPriceCalculate {

    public static void main(String[] args) {
        String calculate = calculate(2, 2, BigDecimal.valueOf(47), BigDecimal.valueOf(37.7));
        System.out.println(calculate);
    }


    public static String calculate(int couponsCount,int gifeCount,BigDecimal proportionYXB,BigDecimal proportionDB){
        if(gifeCount <= 0 || BigDecimal.ZERO.compareTo(proportionYXB) >=0 || BigDecimal.ZERO.compareTo(proportionDB) >=0){
            return "不想买滚";
        }
        /** 初始化变量 */
        BigDecimal actualPrice          = BigDecimal.ZERO;
        BigDecimal actualPriceqb        = BigDecimal.ZERO;
        BigDecimal actualPricedb        = BigDecimal.ZERO;
        BigDecimal onePrice             = BigDecimal.valueOf(399);//礼包单价
        BigDecimal qbDiscount           = BigDecimal.valueOf(0.95);//QB折扣
        BigDecimal couponsDiscount      = BigDecimal.valueOf(0.9);//打折券折扣

        //原始总价
        BigDecimal sumMoney = onePrice.multiply(BigDecimal.valueOf(gifeCount));

        if(couponsCount>0 && couponsCount>=gifeCount){
            actualPrice = onePrice.multiply(couponsDiscount).multiply(BigDecimal.valueOf(gifeCount));
        }else if(couponsCount>0 && couponsCount<gifeCount){
            BigDecimal couponsSum = onePrice.multiply(couponsDiscount).multiply(BigDecimal.valueOf(couponsCount));
            BigDecimal qbSum = onePrice.multiply(BigDecimal.valueOf(gifeCount - couponsCount));
            actualPrice = couponsSum.add(qbSum);
        }else{
            actualPrice = sumMoney;
        }

        System.out.println("扣完优惠券还需："+ actualPrice +" 代币券");

        actualPriceqb = actualPrice.multiply(qbDiscount);


        BigDecimal yXBSum = actualPrice.multiply(proportionDB).divide(BigDecimal.valueOf(0.97),2, BigDecimal.ROUND_HALF_UP);
        actualPricedb = yXBSum.divide(BigDecimal.valueOf(0.97),2, BigDecimal.ROUND_HALF_UP).divide(proportionYXB,2, BigDecimal.ROUND_HALF_UP);


        return "直充RMB：" + actualPriceqb + "元，代币折换需买"+actualPricedb+"RMB的的YXB，相差"+actualPriceqb.subtract(actualPricedb)+"元。";
    }

}
