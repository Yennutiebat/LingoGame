package bep.lingogame.controller;

import bep.lingogame.domain.Word;
import bep.lingogame.service.WordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/word")
public class WordController {
    private WordService wordService;


    public WordController(WordService wordService) {

        this.wordService = wordService;
        }


        @GetMapping
        public List<Word> findAll() {
            return wordService.findAll();
        }
        @PostMapping(consumes = "application/json")
        public Long createNew(@RequestBody Word word) {
            Word newWord = wordService.createNew(word);
            if (newWord != null){
                return newWord.id;
            }else{
                return null;
            }
        }
}