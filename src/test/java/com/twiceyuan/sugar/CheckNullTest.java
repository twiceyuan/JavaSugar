package com.twiceyuan.sugar;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * Created by twiceYuan on 18/10/2016.
 *
 * CheckNull testcase
 */
public class CheckNullTest {

    @Test
    public void test() {

        ComplexModel model1 = testModel1();
        ComplexModel model2 = testModel2();

        CheckNull.of(checker -> checker.check(model1.childModels.size(), model1.childModels.get(0).name))
                .ifPresent(() -> {
                    System.out.println();
                    System.out.println("Model1");
                    System.out.println(model1.childModels.size());
                    System.out.println(model1.childModels.get(0).name);
                    System.out.println();
                    assert true;
                })
                .ifHasNull(() -> {
                    System.out.println();
                    System.out.println("Model1 is bad");
                    System.out.println();
                    assert false;
                });

        CheckNull.of(checker -> checker.check(model2.childModels.size(), model2.childModels.get(0).name))
                .ifPresent(() -> {
                    System.out.println();
                    System.out.println("Model2");
                    System.out.println(model2.childModels.size());
                    System.out.println(model2.childModels.get(0).name);
                    System.out.println();
                    assert false;
                })
                .ifHasNull(() -> {
                    System.out.println();
                    System.out.println("Model2 is bad");
                    System.out.println();
                    assert true;
                });
    }

    private ComplexModel testModel1() {
        ComplexModel model = new ComplexModel();
        model.name = "twiceYuan";
        ComplexModel.ChildModel childModel = new ComplexModel.ChildModel();
        childModel.name = "child";
        model.childModels = Collections.singletonList(childModel);
        return model;
    }

    private ComplexModel testModel2() {
        ComplexModel model = new ComplexModel();
        model.name = "singleYuan";
        return model;
    }

    private static class ComplexModel {

        String name;
        List<ChildModel> childModels;

        static class ChildModel {
            String name;
        }
    }
}
