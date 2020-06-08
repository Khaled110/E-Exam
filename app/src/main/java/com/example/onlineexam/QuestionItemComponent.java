package com.example.onlineexam;

public class QuestionItemComponent {
     String question,option1,option2,option3,option4,trueItem,falseItem,openQuestion,
            degreeOpenQuestion,correctAnswer,gallary,vedio,audio,chapter,level;
    String op1,op2,op3,op4,true1,true2,count1,count2,count3,count4,countFullok,countFullNo,countT,countF,countFullTrue,countFullfalse;
   boolean isMCQ,isTrueandFalse,one, two, three,four,t,f;

    public QuestionItemComponent(String gallary) {
        this.gallary=gallary;
    }
    public QuestionItemComponent() {
    }




    public QuestionItemComponent(String question, String option1, String option2, String option3,
                                 String option4, String trueItem, String falseItem,
                                 String openQuestion, String degreeOpenQuestion, String correctAnswer,
                                 String gallary, String chapter, String level, String op1, String op2,
                                 String op3, String op4, String true1, String true2,String vedio,String audio, boolean isMCQ,
                                 boolean isTrueandFalse,boolean one,boolean two,boolean three,boolean four,boolean t,boolean f) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.trueItem = trueItem;
        this.falseItem = falseItem;
        this.openQuestion = openQuestion;
        this.degreeOpenQuestion = degreeOpenQuestion;
        this.correctAnswer = correctAnswer;
        this.gallary = gallary;
        this.chapter = chapter;
        this.level = level;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
        this.true1 = true1;
        this.true2 = true2;
        this.vedio=vedio;
        this.audio=audio;
        this.isMCQ = isMCQ;
        this.isTrueandFalse = isTrueandFalse;
        this.one=one;
        this.two=two;
        this.three=three;
        this.four=four;
        this.t=t;
        this.f=f;
    }

    public boolean isT() {
        return t;
    }

    public void setT(boolean t) {
        this.t = t;
    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }

    public boolean isOne() {
        return one;
    }

    public void setOne(boolean one) {
        this.one = one;
    }

    public boolean isTwo() {
        return two;
    }

    public void setTwo(boolean two) {
        this.two = two;
    }

    public boolean isThree() {
        return three;
    }

    public void setThree(boolean three) {
        this.three = three;
    }

    public boolean isFour() {
        return four;
    }

    public void setFour(boolean four) {
        this.four = four;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getCount1() {
        return count1;
    }

    public void setCount1(String count1) {
        this.count1 = count1;
    }

    public String getCount2() {
        return count2;
    }

    public void setCount2(String count2) {
        this.count2 = count2;
    }

    public String getCount3() {
        return count3;
    }

    public void setCount3(String count3) {
        this.count3 = count3;
    }

    public String getCount4() {
        return count4;
    }

    public void setCount4(String count4) {
        this.count4 = count4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getTrueItem() {
        return trueItem;
    }

    public void setTrueItem(String trueItem) {
        this.trueItem = trueItem;
    }

    public String getFalseItem() {
        return falseItem;
    }

    public void setFalseItem(String falseItem) {
        this.falseItem = falseItem;
    }

    public String getOpenQuestion() {
        return openQuestion;
    }

    public void setOpenQuestion(String openQuestion) {
        this.openQuestion = openQuestion;
    }

    public String getDegreeOpenQuestion() {
        return degreeOpenQuestion;
    }

    public void setDegreeOpenQuestion(String degreeOpenQuestion) {
        this.degreeOpenQuestion = degreeOpenQuestion;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getGallary() {
        return gallary;
    }

    public void setGallary(String gallary) {
        this.gallary = gallary;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    public String getTrue1() {
        return true1;
    }

    public void setTrue1(String true1) {
        this.true1 = true1;
    }

    public String getTrue2() {
        return true2;
    }

    public void setTrue2(String true2) {
        this.true2 = true2;
    }

    public boolean isMCQ() {
        return isMCQ;
    }

    public void setMCQ(boolean MCQ) {
        isMCQ = MCQ;
    }

    public boolean isTrueandFalse() {
        return isTrueandFalse;
    }

    public void setTrueandFalse(boolean trueandFalse) {
        isTrueandFalse = trueandFalse;
    }
}
