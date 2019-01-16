package controller;

import dao.TaskListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import logic.DataFilter;
import model.Product;

import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {
    @Autowired
    private TaskListDao taskListDao;

    @RequestMapping(value = "/add-list", method = RequestMethod.POST)
    public String addQuestion(@ModelAttribute("list") Product product) {
        taskListDao.addProduct(product);
        return "redirect:/get-products";
    }

    @RequestMapping(value = "/get-products", method = RequestMethod.GET)
    public String getProducts(ModelMap map) {
        prepareModelMap(map, taskListDao.getProducts());
        return "index";
    }

    @RequestMapping(value = "/filter-products", method = RequestMethod.GET)
    public String getProducts(@RequestParam String filter, ModelMap map) {
        Optional<DataFilter> dataFilter = DataFilter.getFilterByName(filter);
        if (dataFilter.isPresent()) {
            prepareModelMap(map, dataFilter.get().filter(productDao));
        }

        return "index";
    }

    private void prepareModelMap(ModelMap map, List<Product> products) {
        map.addAttribute("products", products);
        map.addAttribute("product", new Product());
        map.addAttribute("filter", new Filter());
    }
}
