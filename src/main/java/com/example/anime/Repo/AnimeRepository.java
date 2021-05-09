package com.example.anime.Repo;

import com.example.anime.Model.AnimeModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnimeRepository extends  CrudRepository<AnimeModel,Long>{
    public List<AnimeModel> findAllByType(String type);
    public List<AnimeModel> findAllByCategory(String category);
}