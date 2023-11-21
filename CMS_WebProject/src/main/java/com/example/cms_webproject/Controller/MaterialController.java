package com.example.cms_webproject.Controller;

import com.example.cms_webproject.Model.Material;
import com.example.cms_webproject.Repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MaterialController {
    @Autowired
    private MaterialRepository materialRepository;
    @PostMapping("/materialtreat")//정보 입력 저장
    public Map<String,Object> create(@RequestBody Material material){
        Map<String,Object> resultTable = new HashMap<>();
        if(materialRepository.findByName(material.getName())==null){
            materialRepository.save(material);
            resultTable.put("message","기자재가 입력되었다.");
            resultTable.put("status",201);
        }//정보가 중복되지 않을때 저장한다.
        else{
            resultTable.put("message","동일한 기자재가 존재한다.");
            resultTable.put("status",401);
        }
        return resultTable;
    }

   @GetMapping("/materialtreat")
   public Map<String,Object> getlist(@RequestParam(value = "brand",required = false) List<String> brand,@RequestParam(value = "matter",required = false) List<String> matter, @RequestParam(value = "users",required = false) List<String>uses,@RequestParam(value = "page",required = true) int page,@RequestParam(value = "sort",required = false) String sort){
        Map<String,Object> resultTable = new HashMap<>();//안들어오면 전체를 가르키게 해야하넹....
        page=page-1;
        if(brand ==null){
            brand = materialRepository.findAllBrands();
        }
        if(matter == null){
            matter = materialRepository.findAllMatters();
        }
        if(uses == null){
            uses = materialRepository.findAllUses();
        }
        //분류 한다.

       Pageable pageable = PageRequest.of(page,10);
        List<Material> data = materialRepository.findFilteredMaterials(brand,matter,uses,pageable);
       if(sort != null){
           if(sort.equals("name")){
           System.out.println("여");
           Comparator<Material> nameComparator = Comparator.comparing(Material -> Material.getName());
           Collections.sort(data,nameComparator);
       } else if (sort.equals("pricehigh")) {
           System.out.println("기");
           Comparator<Material> pricehighComparator = new Comparator<Material>() {
               @Override
               public int compare(Material o1, Material o2) {
                   return o2.getPrice() - o1.getPrice();
               }
           };
           Collections.sort(data,pricehighComparator);
       } else if (sort.equals("pricelow")) {
           System.out.println("예");
           Comparator<Material> pricelowComparator = new Comparator<Material>() {
               @Override
               public int compare(Material o1, Material o2) {
                   return o1.getPrice() - o2.getPrice();
               }
           };
           Collections.sort(data,pricelowComparator);
       }
       }
       resultTable.put("data",data);
       System.out.println(2);
       return resultTable;
   }
    @DeleteMapping("/materialtreat")//기자재 order받아서 삭제
    public Map<String, Object> delete(@RequestParam Long orders){
        Map<String,Object> resultTable = new HashMap<>();
        if(materialRepository.findByOrders(orders) != null){
            materialRepository.deleteByOrders(orders);
            resultTable.put("message","해당 orders값의 기자재가 삭제되었습니다.");
            resultTable.put("status",201);
        }
        else{
            resultTable.put("message","해당 orders값을 가진 기자재가 없습니다.");
            resultTable.put("status",401);
        }//삭제하려는 기자재가 없는경우
        return resultTable;
    }
    @GetMapping("/materialtreat/{orders}")
    public Map<String, Object> getmaterial(@PathVariable Long orders){
        Map<String,Object> resultTable = new HashMap<>();
        if(materialRepository.findByOrders(orders)!=null) {
            resultTable.put("기자재 정보",materialRepository.findByOrders(orders));
            resultTable.put("message","해당 orders값의 기자재를 전송하였습니다.");
            resultTable.put("status",201);
        }
        else{
            resultTable.put("message","해당 orders값을 가진 기자재가 없습니다.");
            resultTable.put("status",401);
        }//해당 orders값을 가진 가지재가 없다.
        return resultTable;
    }
    @GetMapping("/materialsearch")
    public Map<String,Object> searchMaterial(@RequestParam String name){
        Map<String, Object> resultTable = new HashMap<>();
        List<Material> materials = materialRepository.findByNameContaining(name);
        if(materials.isEmpty()){
            resultTable.put("message","해당 이름을 가진 기자재가 없습니다.");
            resultTable.put("status",401);
            return resultTable;
        }
        else{
            resultTable.put("data",materials);
            resultTable.put("message","해당 이름을 가진 기자재가 있습니다.");
            resultTable.put("status",201);
            return resultTable;
        }
    }
}
