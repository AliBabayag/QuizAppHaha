package com.example.demo1;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.fxml.FXML;
import com.example.demo1.model.QuestionModel;
import com.example.demo1.model.Quiz;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {
    @FXML
    private ListView<QuestionModel> listView;

    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldA;
    @FXML
    private TextField fieldB;
    @FXML
    private TextField fieldC;
    @FXML
    private TextField fieldD;

    public void initialize() throws Exception {
        File file = new File("data.json");

        if (!file.exists())
            return;



        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        Quiz quiz= objectMapper.readValue(file, Quiz.class);





        List<QuestionModel> modelList = quiz.getQuestions().stream()
                .map(QuestionModel.Question::toModel)
                .collect(Collectors.toList());

        listView.setItems(
                FXCollections.observableArrayList(modelList)
        );
    }

    public void onAdd() {
        QuestionModel question = new QuestionModel();
        question.text.setValue(fieldName.getText());
        question.answerA.setValue(fieldA.getText());
        question.answerB.setValue(fieldB.getText());
        question.answerC.setValue(fieldC.getText());
        question.answerD.setValue(fieldD.getText());

        listView.getItems().add(question);
    }

    public void onSave() throws Exception {
        var list = listView.getItems()
                .stream()
                .map(QuestionModel::toData)
                .collect(Collectors.toList());

        var writer = new ObjectMapper();
        writer.enable(SerializationFeature.INDENT_OUTPUT);
        writer.writeValue(new File("data.json"), new Quiz(list));
    }

    private QuestionModel prevSelectedQuestion;

    public void onEdit() {
        QuestionModel question = listView.getSelectionModel().getSelectedItem();
        if (question == null)
            return;

        if (prevSelectedQuestion != null) {
            prevSelectedQuestion.text.unbind();
            prevSelectedQuestion.answerA.unbind();
            prevSelectedQuestion.answerB.unbind();
            prevSelectedQuestion.answerC.unbind();
            prevSelectedQuestion.answerD.unbind();
        }

        fieldName.setText(question.text.getValue());
        fieldA.setText(question.answerA.getValue());
        fieldB.setText(question.answerB.getValue());
        fieldC.setText(question.answerC.getValue());
        fieldD.setText(question.answerD.getValue());

        question.text.bind(fieldName.textProperty());
        question.answerA.bind(fieldA.textProperty());
        question.answerB.bind(fieldB.textProperty());
        question.answerC.bind(fieldC.textProperty());
        question.answerD.bind(fieldD.textProperty());

        prevSelectedQuestion = question;
    }}