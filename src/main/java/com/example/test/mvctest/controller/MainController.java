package com.example.test.mvctest.controller;


import com.example.test.mvctest.repository.model.Product;
import com.example.test.mvctest.service.ProductSevice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    ProductService productService;

        @GetMapping("/index")
        String getView(ModelMap modelMap){

            for (Product product : productService.getList()) {
                modelMap.addAttribute("product", product.getProName());
                System.out.println(productService.getList());
            }
            return "index";
        }

    @GetMapping("/add")
    String addProduct(){
        Product product = new Product(100,"Cocacola");
            productService.addProduct(product);

        return "redirect:/";
    }


    @GetMapping("/pro")
    String getByParam(@Valid @RequestParam int id,@RequestParam String name){
        System.out.println(id);
        System.out.println(name);
        Product product = new Product(id,name);
        productService.addProduct(product);
        System.out.println("product at complete!!");
        return "redirect:/";
    }

    @GetMapping("/pro/{id}/{name}")
    String getPath(@PathVariable int id, @PathVariable String name,@RequestParam boolean check){
        System.out.println(id);
        System.out.println(name);
        System.out.println(check);
        Product product = new Product(id,name);
        productService.addProduct(product);
        System.out.println("product at complete!!");
        return "redirect:/";
    }


    @RequestMapping("/form")
    public String getForm(ModelMap modelMap){
            modelMap.addAttribute("product",new Product());
           modelMap.addAttribute("product1",new Product(10,"cocacolaUU"));
            return "form";
    }

    @PostMapping("/upload")
    public String postProd(@Valid @ModelAttribute Product product , BindingResult result){
            if(result.hasErrors()){
                System.out.println("ERROR");
                return "form";
            }else {
               System.out.println("You good to go");
              System.out.println(product);
             productService.addProduct(product);
            return  "redirect:/form";
    }}

    @GetMapping("/fileform")
   public String getFile(){
            return "file_form";
    }
    @PostMapping("/testing")
  public  String getUploadForm(@RequestParam List<MultipartFile> file){

            for (MultipartFile infile : file){
                System.out.println(infile.getOriginalFilename());
                if (!infile.isEmpty()){

                    try {
                        Files.copy(infile.getInputStream(), Paths.get( "/home/lovkimlinh/Desktop/testfile/",infile.getOriginalFilename()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

     return "redirect:/fileform";
    }




    @GetMapping("/table")
    public String getTable(ModelMap modelMap){
            modelMap.addAttribute("students",productService.getList());
            return "show-data";
    }
    @GetMapping("/delete")
    public String removeData(){
                productService.getList().clear();
                if (productService.getList().isEmpty()){
                    System.out.println("Data have been clear");
                }else {
        System.out.println("Data cant be clear");}
            return "redirect:/table";
    }

}
