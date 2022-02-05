/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Player {
    private SimpleIntegerProperty id;    
    private SimpleStringProperty name;
    private SimpleStringProperty email;
    private SimpleStringProperty password;
    private SimpleIntegerProperty points;
    private SimpleStringProperty status;
    private SimpleStringProperty classify;

    public Player(int id,String name,String email,String password,int points){
        this.id=new SimpleIntegerProperty(id);
        this.name=new SimpleStringProperty(name);
        this.email=new SimpleStringProperty(email);
        this.password=new SimpleStringProperty(password);
        this.points=new SimpleIntegerProperty(points);
        this.status=new SimpleStringProperty("");
        this.classify=new SimpleStringProperty("");
    }


    
    public Integer getId(){
        return id.get();
    }
    public void setId(Integer id){
        this.id.set(id);
    }
    public String getName(){
        return name.get();
    }
    public void setName(String name){
        this.name.set(name);
    }
    public String getEmail(){
        return email.get();
    }
    public void setEmail(String email){
        this.email.set(email);
    }
    public String getPassword(){
        return password.get();
    }
    public void setPassword(String password){
        this.password.set(password);
    }
    public Integer getPoints(){
        return points.get();
    }
    public void setPoints(Integer points){
        this.points.set(points);
    }
    public String getStatus(){
        return status.get();
    }
    public void setStatus(Integer status){
        if(status==0){
           this.status.set("Offline");
       }
       else if(status==1){
           this.status.set("Online");
       }
       else if(status==2){
           this.status.set("Busy");
       }
    }
    
    public void setClassify(Integer point){
       if(point>=0 && point<=50){
           this.classify.set("Beginner");
       }
       else if(point>=50 && point<=100){
           this.classify.set("Intermediate");
       }
       else if(point>100){
           this.classify.set("Professional");
       }
       
   }
    public String getClassify(){
        return classify.get();
    }
    
    
}
