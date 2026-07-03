package model;

/**
 * ============================================================================
 * Promotion
 * ============================================================================
 */
public class Promotion {

    private int promotionId;

    private String promotionName;

    private double discount;

    public Promotion() {
    }

    public Promotion(int promotionId,
                     String promotionName,
                     double discount) {

        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.discount = discount;

    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}