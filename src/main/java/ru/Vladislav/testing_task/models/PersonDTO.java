package ru.Vladislav.testing_task.models;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PersonDTO {
    @Column(name="name")
    @NotEmpty(message ="Вы не заполнили поле имени")
    @Size(min=2,max=20,message = "Длина имени должна попадать в диапазон от 2 до 20")
    @Pattern(regexp="[A-Z]\\w+",message = "Вы неправильно ввели имя")
    private String name;
    @Column(name="number")
    @Size(min=11,max=12,message = "Номер должен иметь тип 8**********")
    @Pattern(regexp = "\\d{11}",message = "Номер должен иметь тип 8**********")
    private String number;

    @Column(name="date")
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
