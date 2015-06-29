package br.com.atlantico.mychronos.test;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Assert;

import br.com.atlantico.mychronos.R;
import br.com.atlantico.mychronos.activities.MainActivity;

/**
 * Created by joao_nascimento on 26/06/2015.
 */
public class UIConfigurar extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public UIConfigurar() {
        super(MainActivity.class);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }


    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testJonardaOitoHr(){
        solo.clickOnMenuItem("Configurações");
        Assert.assertTrue(solo.searchText("Jornada Diária"));
        solo.clickOnRadioButton(0);
        Assert.assertTrue(solo.isRadioButtonChecked(0));
    }

    public void testJonardaSeisHr(){
        solo.clickOnActionBarHomeButton();
        solo.clickOnText("Configurações");
        solo.clickOnRadioButton(1);
        Assert.assertTrue(solo.isRadioButtonChecked(1));
    }

    public void testJonardaQuatroHr(){
        solo.clickOnMenuItem("Configurações");
        solo.clickOnRadioButton(2);
        Assert.assertTrue(solo.isRadioButtonChecked(2));
    }

}
