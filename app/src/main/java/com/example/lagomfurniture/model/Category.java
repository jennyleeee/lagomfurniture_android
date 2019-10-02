package com.example.lagomfurniture.model;

public class Category {

    private String title;
    private int image;
    private int itemViewType;

    public Category(String title, int image, int itemViewType) {
        this.title = title;
        this.image = image;
        this.itemViewType = itemViewType;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public int getItemViewType() {
        return itemViewType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
    }

    @Override
    public String toString() {
        return "Category{" + "title='" + title + '\'' + ", image=" + image + ", itemViewType=" + itemViewType + '}';
    }
}
