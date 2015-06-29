package br.com.atlantico.mychronos.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.junit.Assert;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.activities.MainActivity;
import br.com.atlantico.mychronos.db.ChronosDBHelper;

import static br.com.atlantico.mychronos.R.id.taskList;

/**
 * Created by joao_nascimento on 26/06/2015.
 */
public class UITarefas extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public UITarefas() {
        super(MainActivity.class);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
       // ChronosDBHelper helper = new ChronosDBHelper(getActivity());

       // helper.onUpgrade(helper.getWritableDatabase(), 1, 1);
    }


    @Override
    public void tearDown() throws Exception {
        //ChronosDBHelper helper = new ChronosDBHelper(getActivity());
        //helper.onUpgrade(helper.getWritableDatabase(), 1, 1);
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testEditTarefa(){
        solo.clickOnText("Tarefas");
        solo.clickLongOnText("Robotium");
        solo.waitForDialogToOpen();
        solo.enterText(0, "");
        solo.enterText(0, "RobotiumTreinamento");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose();

    }

    public void testAddTarefa(){
        solo.clickOnText("Tarefas");
        solo.clickOnView(solo.getView(R.id.fabAddTask));
        solo.waitForDialogToOpen();
        Assert.assertTrue(solo.searchText("OK"));
        Assert.assertTrue(solo.searchText("Cancelar"));
        solo.enterText(0, "Robotium");
        Assert.assertTrue(solo.waitForText("Robotium"));
        solo.clickOnButton("OK");
        solo.waitForDialogToClose();

    }

    public void testTarefaExiste(){
        solo.clickOnText("Tarefas");
        solo.clickOnView(solo.getView(R.id.fabAddTask));
        solo.waitForDialogToOpen();
        solo.enterText(0, "Limbo");
        solo.clickOnButton("OK");
        Assert.assertTrue(solo.waitForText("A tarefa já existe"));

    }

 }
