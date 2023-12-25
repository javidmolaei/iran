package ir.javid.iran.service;

import ir.javid.iran.model.State;
import ir.javid.iran.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: Mr.javidmolaei
 */

@Service
public class StateService {
    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public void initializeStates(){
        if (stateRepository.count() == 0){
            addState("آذربایجان شرقی");
            addState("آذربایجان غربی");
            addState("اردبیل");
            addState("اصفهان");
            addState("البرز");
            addState("ایلام");
            addState("بوشهر");
            addState("تهران");
            addState("چهارمحال و بختیاری");
            addState("خراسان جنوبی");
            addState("خراسان رضوی");
            addState("خراسان شمالی");
            addState("خوزستان");
            addState("زنجان");
            addState("سمنان");
            addState("سیستان و بلوچستان");
            addState("فارس");
            addState("قزوین");
            addState("قم");
            addState("کردستان");
            addState("کرمان");
            addState("کرمانشاه");
            addState("کهکیلویه و بویر احمد");
            addState("گلستان");
            addState("گیلان");
            addState("لرستان");
            addState("مازندران");
            addState("مرکزی");
            addState("هرمزگان");
            addState("همدان");
            addState("یزد");
        }
    }


    private void addState(String stateName){
        stateRepository.save(new State(stateName));
    }

    public List<State> getAllStates() {
        return this.stateRepository.findAll();
    }
}
