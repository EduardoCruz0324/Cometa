package com.product;

import java.util.ArrayList;

public class Category{
    // atributos de la clase category
    Integer category_id;
    String  category;
    String  tag;
    Integer status;
    static ArrayList<Category> categoriesArray = new ArrayList<>();

    //constructor por parametros 
    public Category(Integer category_id, String category, String tag, Integer status){
        this.category_id = category_id;
        this.category = category;
        this.tag = tag;
        this.status = status;   

        if(checkCategories()){
            categoriesArray.add(this);
        }else{System.out.println("ese ID, categoria o tag ya existe. No se pueden agregar repetidos");}        
    }
    
    // constructor vacio
    public Category(){
        return;
    }


    // función para verificar que el category_id, category y tag deben ser únicos
    // regresa un booleano true en caso de que no exista ningun atributo igual.
    public boolean checkCategories(){
        for(Category categoryAux : categoriesArray){
            if(categoryAux.category_id.equals(this.category_id)){
                return false;
            }
            if(categoryAux.category.equals(this.category)){
                return false;
            }
            if(categoryAux.tag.equals(this.tag)){
                return false;
            }
        }
        return true;
    }

    // funcion que muestra en consola una lista con las categorías registradas con status 1
    //ejemplo: [{1,”Lentes”,”Lts”,1}, {2, “Relojes”, “Rljs”,1}]        
    public void getCategories(){
        if(categoriesArray.isEmpty()){
            System.out.println("No existen categorías registradas");
            return;            
        }

        System.out.print("[");
        for(Category categoryAux : categoriesArray){
            if(categoryAux.status == 1){
                System.out.print("{"+categoryAux.category_id + "," + categoryAux.category + "," + categoryAux.tag + "," + categoryAux.status+"},");                
            }
        }
        System.out.println("]");
    }

    // funcion que elimina una categoria id
    public void deleteCategory(Integer category_id){
        for(int i=0;i<categoriesArray.size();i++){
            if(categoriesArray.get(i).category_id.equals(category_id)){
                categoriesArray.remove(i);
            }
        }
    }    
}