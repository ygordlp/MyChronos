package br.com.atlantico.mychronos;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import br.com.atlantico.mychronos.activities.MainActivity;

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
        solo.assertCurrentActivity("Expected MyChronos activity", MainActivity.class);

    }

    //Verificando se consigo realizar a troca de aba
    public void testAlterarTab(){
        boolean TabNaoEncontrada = solo.searchText("Tarefas");
        solo.clickOnText("Tarefas");
        solo.clickOnText("Relatório");
        solo.goBack();

    }

    //Entrando na tela de configuração
    public void testTelaConfiguracao(){
        solo.clickOnMenuItem("Configurações");
        solo.waitForText("Configurações");
        boolean TextonaoEncontrado = solo.searchText("Configurações");
        solo.sleep(2000);
        solo.goBack();

    }

    //Selecionando menu Lateral - by Helano Mesquita
    public void testMenuLateral(){
        solo.clickOnActionBarHomeButton();
        solo.clickOnText("Sobre");
        solo.goBack();

    }




}
