package com.product;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private Integer category_id;
    private String category;
    private String tag;
    private Integer status;

    // Lista estática para simular persistencia en memoria
    private static List<Category> categoriesArray = new ArrayList<>();

    // Constructor vacío
    public Category() {
    }

    // Constructor por parámetros
    public Category(Integer category_id, String category, String tag, Integer status) {
        this.category_id = category_id;
        this.category = category;
        this.tag = tag;
        this.status = status;
        
        // Lógica de validación antes de agregar a la lista
        if (checkCategories()) {
            categoriesArray.add(this);
        } else {
            System.out.println("Error: El ID, categoría o tag ya existe.");
        }
    }

    public boolean checkCategories() {
        for (Category aux : categoriesArray) {
            if (aux.category_id.equals(this.category_id) || 
                aux.category.equalsIgnoreCase(this.category) || 
                aux.tag.equalsIgnoreCase(this.tag)) {
                return false;
            }
        }
        return true;
    }

    public static void getCategories() {
        if (categoriesArray.isEmpty()) {
            System.out.println("No existen categorías registradas");
            return;
        }

        StringBuilder sb = new StringBuilder("[");
        for (Category aux : categoriesArray) {
            if (aux.status != null && aux.status == 1) {
                sb.append(String.format("{%d, \"%s\", \"%s\", %d}, ", 
                    aux.category_id, aux.category, aux.tag, aux.status));
            }
        }
        
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Quitar la última coma
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public static void deleteCategory(Integer id) {
        categoriesArray.removeIf(c -> c.category_id.equals(id));
    }

    // Getters y Setters
    public Integer getCategory_id() { return category_id; }
    public void setCategory_id(Integer category_id) { this.category_id = category_id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}