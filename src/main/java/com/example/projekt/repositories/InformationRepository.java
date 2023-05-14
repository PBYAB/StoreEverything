package com.example.projekt.repositories;

import com.example.projekt.data.Category;
import com.example.projekt.data.Information;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
@SessionScope
public class InformationRepository {
    private List<Information> informations = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public InformationRepository(){
        System.out.println("constructor");
        categories.add(new Category("strona www"));
        informations.add(new Information("1","pb.com","wazna strona",new Category("strona www")));
    }
    /*
    public String getInformationFromCategory(String category){
        StringBuilder sb = new StringBuilder();
        for(Information info : informations){
            if(info.getCategory().getName().equals(category)){
                sb.append(info.getName());
                sb.append(", ");
            }
        }
        return sb.toString();
    }*/
}
