package br.com.atlantico.mychronos.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.activities.MainActivity;
import br.com.atlantico.mychronos.db.ChronosDBHelper;
import br.com.atlantico.mychronos.db.TimestampEntry;

import static br.com.atlantico.mychronos.R.id.txtSecondOut;

/**
 * Created by joao_nascimento on 05/06/2015.
 */
public class UITest extends ActivityInstrumentationTestCase2<MainActivity>{

    private Solo solo;

    public UITest() {
        super(MainActivity.class);

    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        ChronosDBHelper helper = new ChronosDBHelper(getActivity());
        helper.onUpgrade(helper.getWritableDatabase(), 1, 1);
    }

    @Override
    public void tearDown() throws Exception {
        ChronosDBHelper helper = new ChronosDBHelper(getActivity());
        helper.onUpgrade(helper.getWritableDatabase(), 1, 1);
       solo.finishOpenedActivities();
        super.tearDown();
    }

    //Verificando se está na tela principal
    public void testTelaPrincipal() throws Exception{
        solo.assertCurrentActivity("Expected MyChronos Activity", MainActivity.class);

    }

    //Verificando se consigo realizar a troca de aba
    public void testAlterarTab(){
        solo.clickOnText("Tarefas");
        solo.clickOnText("Relatório");
        solo.clickOnText("Ponto");

    }

    //Entrando na tela de configuração
    public void testTelaConfiguracao(){
        solo.clickOnMenuItem("Configurações");
        Assert.assertTrue(solo.searchText("Jornada Diária"));
        solo.clickOnRadioButton(0);
        solo.clickOnRadioButton(1);
        solo.clickOnRadioButton(2);
    }

    //Selecionando menu Lateral
    public void testMenuLateral(){
        solo.clickOnActionBarHomeButton();
        Assert.assertTrue(solo.searchText("Ponto"));
        Assert.assertTrue(solo.searchText("Tarefas"));
        Assert.assertTrue(solo.searchText("Relatório"));
        Assert.assertTrue(solo.searchText("Configurações"));
        Assert.assertTrue(solo.searchText("Sobre"));
        solo.clickOnText("Sobre");


    }

    public void testBtnCancelar(){
        solo.clickOnText("Ponto");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.waitForDialogToOpen();
        Assert.assertTrue(solo.searchText("OK"));
        Assert.assertTrue(solo.searchText("Cancelar"));
        solo.clickOnButton("Cancelar");
    }

    //Teste clicando no botão de bater ponto
    public void testBaterPonto(){
        solo.clickOnText("Ponto");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 8, 01);
        solo.waitForDialogToOpen();
        Assert.assertTrue(solo.searchText("OK"));
        Assert.assertTrue(solo.searchText("Cancelar"));
        solo.clickOnButton("OK");
        solo.clickOnButton("Bater Ponto");
        solo.setTimePicker(0, 11, 30);
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 12, 31);
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 17, 02);
        solo.clickOnButton("OK");
        solo.waitForDialogToClose();
        //Verificando se a hora trabalhada é igual a esperada.
        Assert.assertTrue("Horas trabalhadas: 8h", (((TextView) solo.getView(R.id.txtWorkedTime)).getText()).equals("8h"));
        solo.takeScreenshot("Ponto-horas_trabalhadas");

    }

    //Teste previsão de Saída: Entrada - 8:00 Saída - 12:00 Entrada 13:00 Saída: 17:00
    public void testPrevisaoSaida() {
        solo.clickOnText("Ponto");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.waitForDialogToOpen();
        Assert.assertTrue(solo.searchText("OK"));
        Assert.assertTrue(solo.searchText("Cancelar"));
        //Apenas testando o botão Cancelar
        solo.clickOnButton("Cancelar");
        //Continuando...
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 8, 00);
        solo.clickOnButton("OK");
        solo.clickOnButton("Bater Ponto");
        solo.setTimePicker(0, 11, 30);
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 12, 30);
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 17, 00);
        solo.clickOnButton("OK");
        solo.waitForDialogToClose();
        Assert.assertTrue("Previsão de Saída: 17:00", (((TextView) solo.getView(R.id.txtTimToLeave)).getText()).equals("17:00"));
        solo.takeScreenshot("Ponto-previsao_saida");

    }

    public void testBtnVoltar(){
        solo.clickOnText("Ponto");
        solo.searchButton("<<");
        solo.clickOnView(solo.getView(R.id.btnPrevDate));
    }

    }

}
