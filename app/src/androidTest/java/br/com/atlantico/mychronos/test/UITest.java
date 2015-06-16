package br.com.atlantico.mychronos.test;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Test;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.activities.MainActivity;

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
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
       solo.finishOpenedActivities();
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
    }

    //Selecionando menu Lateral
    public void testMenuLateral(){
        solo.clickOnActionBarHomeButton();
        boolean lateral = solo.searchText("Ponto") && solo.searchText("Tarefas") && solo.searchText("Relatório") && solo.searchText("Configurações") && solo.searchText("Sobre");
        assertTrue("Falta algum menu lateral", lateral);
        solo.clickOnText("Sobre");


    }

    //Teste clicando no botão de bater ponto
    public void testBaterPonto(){
        solo.clickOnText("Ponto");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 8, 01);
        solo.waitForDialogToOpen();
        assertTrue(solo.searchButton("OK") && solo.searchButton("Cancelar")); ;
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.btnCheck));
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
        boolean campos = solo.searchText("Horas trabalhadas:") && (((TextView)  solo.getView(R.id.txtWorkedTime)).getText()).equals("8h");
        assertTrue("Não encontrada a mensagem, Horas trabalhadas",campos);
        solo.takeScreenshot("Ponto-horas_trabalhadas");

    }

    //Teste previsão de Saída: Entrada - 8:00 Saída - 12:00 Entrada 13:00 Saída: 17:00
    public void testPrevisaoSaida() {
        solo.clickOnText("Ponto");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 8, 00);
        solo.waitForDialogToOpen();
        assertTrue(solo.searchButton("OK") && solo.searchButton("Cancelar"));
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 12, 00);
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 13, 00);
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.setTimePicker(0, 17, 00);
        solo.clickOnButton("OK");
        solo.waitForDialogToClose();
        boolean campos = solo.searchText("Previsão de Saída:") && (((TextView)  solo.getView(R.id.txtTimToLeave)).getText()).equals("17:00");
        assertTrue("Hora de saída não é igual a hora esperada",campos);
        solo.takeScreenshot("Ponto-previsao_saida");

    }

    //Testando o botão cancelar do dialog
    public void testBotaoCancelar(){
        solo.clickOnText("Ponto");
        solo.clickOnView(solo.getView(R.id.btnCheck));
        solo.waitForDialogToOpen();
        assertTrue(solo.searchButton("OK") && solo.searchButton("Cancelar")); ;
        solo.clickOnButton("Cancelar");
        solo.waitForDialogToClose();

    }

}
