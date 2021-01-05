package com.tm.taskManagement

class Employee {
    private String name;
    private int age;

    Employee(String name, int age) {
        this.name = name
        this.age = age
    }

    int getAge() {
        return age
    }

    void setAge(int age) {
        this.age = age
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }
}
