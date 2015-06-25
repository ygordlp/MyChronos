package br.com.atlantico.mychronos.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import br.com.atlantico.mychronos.R;

/**
 * Created by pereira_ygor on 25/06/2015.
 */
public class TextInputDialog  extends DialogFragment {
    public interface TextInputListner{
        void onTextInput(String text, Object tagItem);
    }

    private TextInputListner listner;
    private Object tagItem;
    private String text;

    public void setListner(TextInputListner listner){
        this.listner = listner;
    }

    public void setTagItem(Object tagItem){
        this.tagItem = tagItem;
    }

    public void setText(String text){
        this.text = text;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.input_layout, null);
        final EditText txtInput = (EditText) view.findViewById(R.id.txtInput);

        if(text != null){
            txtInput.setText(text);
        }

        builder.setPositiveButton(R.string.lbl_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String text = txtInput.getText().toString();
                if(listner != null){
                    listner.onTextInput(text, tagItem);
                }
            }
        }).setNegativeButton(R.string.lbl_cancel, null).setView(view);
        return builder.create();
    }
}
