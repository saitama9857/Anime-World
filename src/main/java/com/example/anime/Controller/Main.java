package com.example.anime.Controller;

import com.example.anime.Model.AnimeModel;
import com.example.anime.Repo.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Optional;

@EnableSwagger2
@Controller
public class Main {
    @Autowired
    private AnimeRepository animeRepository;

    @GetMapping("/")
    public String main(Model model){
        Iterable<AnimeModel> trending = animeRepository.findAllByType("trending");
        Iterable<AnimeModel> popular = animeRepository.findAllByType("popular");
        Iterable<AnimeModel> recently = animeRepository.findAllByType("recently");
        model.addAttribute("trending",trending);
        model.addAttribute("popular",popular);
        model.addAttribute("recently",recently);
        return "Home";
    }

    @GetMapping("/About")
    public String about(Model model){
        Iterable<AnimeModel> animeAll = animeRepository.findAll();
        model.addAttribute("animeAll",animeAll);
        return "OurBlog";
    }
    @GetMapping("/Contact")
    public String contact(){
        return "Contact";
    }

    @GetMapping("/Categories")
    public String category(){
        return "Categories";
    }

    @GetMapping("/anime-add")
    public String animeAdd(){
        return "anime-Add";
    }

    @PostMapping("/anime-add")
    public String newAnime(@RequestParam String name, @RequestParam String image, @RequestParam String types, @RequestParam String info, Model model){
        AnimeModel anime = new AnimeModel(name,image,types,info);
        animeRepository.save(anime);
        return "redirect:/";
    }

    @GetMapping("/anime/{id}")
    public String animeMore(@PathVariable(value = "id") long id, Model model){
        if(!animeRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<AnimeModel> anime = animeRepository.findById(id);
        ArrayList<AnimeModel> res = new ArrayList<>();
        anime.ifPresent(res::add);
        model.addAttribute("anime",res);
        return "Anime-More";
    }

    @PostMapping("/anime/{id}/delete")
    public String animeDelete(@PathVariable(value = "id") long id){
        AnimeModel anime = animeRepository.findById(id).orElseThrow();
        animeRepository.delete(anime);
        return "redirect:/";
    }

    @GetMapping("/anime/{id}/edit")
    public String animeEdit(@PathVariable(value = "id") long id, Model model) {
        if(!animeRepository.existsById(id)) {
            return "redirect:/";
        }

        Optional<AnimeModel> anime = animeRepository.findById(id);
        ArrayList<AnimeModel> res = new ArrayList<>();
        anime.ifPresent(res::add);
        model.addAttribute("anime", res);
        return "Anime-edit";
    }

    @PostMapping("/anime/{id}/edit")
    public  String animeEditPost(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String info, @RequestParam String url, Model model){
        AnimeModel anime = animeRepository.findById(id).orElseThrow();
        anime.setName(name);
        anime.setInfo(info);
        anime.setImage(url);
        animeRepository.save(anime);
        return  "redirect:/";

    }
}
