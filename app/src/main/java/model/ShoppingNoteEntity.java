package model;

import java.sql.Timestamp;

/**
 * Created by samah on 23/03/2016.
 */
public class ShoppingNoteEntity extends NoteEntity {

    private String productToBuy;
    private String productCategory;

    public ShoppingNoteEntity(String noteType, String notePriority, long userId, Timestamp noteDateCreation, boolean isDone, boolean isActive,
                              boolean isTextCategorized, boolean isSynchronized, String productToBuy, String productCategory ) {
        super(noteType, notePriority, userId, noteDateCreation, isDone, isActive, isTextCategorized, isSynchronized);
        this.productToBuy = productToBuy;
        this.productCategory = productCategory;
    }

    public String getProductToBuy() {
        return productToBuy;
    }

    public void setProductToBuy(String productToBuy) {
        this.productToBuy = productToBuy;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
