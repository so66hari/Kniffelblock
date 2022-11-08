package com.example.kniffelblock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import static android.content.ContentValues.TAG;

public class ButtonDialogs extends DialogFragment {

    private Context context;
    private boolean confirm;

    public ButtonDialogs(Context context) {
        this.context = context;
    }

    private void setBoolean(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean getBoolean() {
        return this.confirm;
    }

    public AlertDialog getDialog(Button button) {

        final AlertDialog mAlertdialog = new AlertDialog.Builder(context).create();
        String button_label = button.getText().toString();

        switch (button_label) {
            default:
                return mAlertdialog;
            case "Einser":
                Log.d(TAG, "getDialog: einserText");
                mAlertdialog.setTitle("Einser");
                mAlertdialog.setMessage("Nur die Würfel mit der Augenzahl 1 werden zusammen gezählt.\n\n Beispiel: 1, 1, 1, 4, 5 \n 1 + 1 + 1 = 3 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Zweier":
                Log.d(TAG, "getDialog: zweierText");
                mAlertdialog.setTitle("Zweier");
                mAlertdialog.setMessage("Nur die Würfel mit der Augenzahl 2 werden zusammen gezählt.\n\n Beispiel: 2, 2, 1, 5, 6 \n 2 + 2  = 4 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Dreier":
                Log.d(TAG, "getDialog: dreierText");
                mAlertdialog.setTitle("Dreier");
                mAlertdialog.setMessage("Nur die Würfel mit der Augenzahl 3 werden zusammen gezählt.\n\n Beispiel: 3, 3, 1, 2, 6 \n 3+3+3 = 9");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Vierer":
                Log.d(TAG, "getDialog: viererText");
                mAlertdialog.setTitle("Vierer");
                mAlertdialog.setMessage("Nur die Würfel mit der Augenzahl 4 werden zusammen gezählt.\n\n Beispiel: 4, 4, 4, 4, 3 \n 4 + 4 + 4 + 4 = 16 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Fünfer":
                Log.d(TAG, "getDialog: fuenferText");
                mAlertdialog.setTitle("Fünfer");
                mAlertdialog.setMessage("Nur die Würfel mit der Augenzahl 5 werden zusammen gezählt.\n\n Beispiel: 5, 5, 1, 4, 6 \n 5 + 5 = 10 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Sechser":
                Log.d(TAG, "getDialog: sechserText");
                mAlertdialog.setTitle("Sechser");
                mAlertdialog.setMessage("Nur die Würfel mit der Augenzahl 6 werden zusammen gezählt.\n\n Beispiel: 6, 6, 6, 1, 3 \n 6 + 6 + 6 = 18 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Bonus":
                Log.d(TAG, "getDialog: bonusText");
                mAlertdialog.setTitle("Bonus");
                mAlertdialog.setMessage("Wenn in der oberen Tabelle eine Punktzahl von min. 65 Punkten erreicht wurde, gibt es 35 Bonuspunkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Dreierpasch":
                Log.d(TAG, "getDialog: dreierpaschText");
                mAlertdialog.setTitle("Dreierpasch");
                mAlertdialog.setMessage("Wenn min. dreimal dieselbe Augenzahl gewürfelt wurde, werden alle Augenzahlen zusammenaddiert. \n\n Beispiel: 3, 3, 3, 5, 1 \n 3 + 3 + 3 + 5 + 1 = 15 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Viererpasch":
                Log.d(TAG, "getDialog: viererpaschText");
                mAlertdialog.setTitle("Viererpasch");
                mAlertdialog.setMessage("Wenn min. viermal dieselbe Augenzahl gewürfelt wurde, werden alle Augenzahlen zusammenaddiert. \n\n Beispiel: 3, 3, 3, 3, 1: \n 3 + 3 + 3 + 3 + 1 = 13 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Full House":
                Log.d(TAG, "getDialog: fullHouseText");
                mAlertdialog.setTitle("Full House");
                mAlertdialog.setMessage("Für einen Zweierpasch und Dreierpasch in einem Wurf gibt es 25 Punkte. \n\n Beispiel: 2, 2, 4, 4, 4 = 25 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Kl. Straße":
                Log.d(TAG, "getDialog: klStrText");
                mAlertdialog.setTitle("Kleine Straße");
                mAlertdialog.setMessage("Für vier aufeinanderfolgende Augenzahlen gibt es 30 Punkte. \n\n Beispiel: 1, 2, 3, 4, 6 = 30 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Gr. Straße":
                Log.d(TAG, "getDialog: grStrText");
                mAlertdialog.setTitle("Große Straße");
                mAlertdialog.setMessage("Für fünf aufeinanderfolgende Augenzahlen gibt es 40 Punkte. \n\n Beispiel: 2, 3, 4, 5, 6 = 40 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Kniffel":
                Log.d(TAG, "getDialog: kniffelText");
                mAlertdialog.setTitle("Kniffel");
                mAlertdialog.setMessage("Für fünf gleiche Augenzahlen gibt es 50 Punkte. \n\n Beispiel: 1, 1, 1, 1, 1 = 50 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                mAlertdialog.show();
                return mAlertdialog;

            case "Chance":
                Log.d(TAG, "getDialog: chanceText");
                mAlertdialog.setTitle("Chance");
                mAlertdialog.setMessage("Augenzahlen werden zusammengezählt, egal welche würfelkombination. \n Beispiel: 1, 3, 3, 5, 6 = 18 Punkte");
                mAlertdialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertdialog.dismiss();
                    }
                });
                return mAlertdialog;
        }
    }

    public void getGameWarning(final Context context) {
        this.context = context;
        final androidx.appcompat.app.AlertDialog mAlertDialog = new androidx.appcompat.app.AlertDialog.Builder(context).create();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        LayoutInflater inflater = LayoutInflater.from(context);
        View popUp = inflater.inflate(R.layout.warning_game, null);
        CheckBox check = popUp.findViewById(R.id.checkBox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    editor.putBoolean("check_warning", false);
                    Log.d(TAG, "onCheckedChanged: warnings disabled");
                    editor.apply();
                } else {
                    editor.putBoolean("check_warning", true);
                    Log.d(TAG, "onCheckedChanged: warnings enabled");
                    editor.apply();
                }
            }
        });
        mAlertDialog.setTitle("Spiel überschreiben");
        mAlertDialog.setMessage("Beim Starten eines neuen Spiels wird das alte Spiel überschrieben.");
        mAlertDialog.setView(popUp, 75, 0, 75, 0);
        mAlertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAlertDialog.dismiss();
                getNewGame(context);
            }
        });
        mAlertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, "Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog.show();
    }

    public void getNewGame(final Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        final View edit_playercount = inflater.inflate(R.layout.edit_playercount, null);
        androidx.appcompat.app.AlertDialog newGame = new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Spielerzahl festlegen")
                .setPositiveButton("Start", null)
                .setNegativeButton("Abbrechen", null)
                .create();
        newGame.setView(edit_playercount, 75, 0, 75, 0);
        final androidx.appcompat.app.AlertDialog finalNewGame = newGame;
        newGame.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button start = finalNewGame.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText player = edit_playercount.findViewById(R.id.editText);
                        String string = player.getText().toString();
                        if (!(string.equals("") || string.equals("0"))) {
                            Intent intent = new Intent();
                            intent.putExtra("newButton", string);
                            intent.setClass(context, GameActivity.class);
                            context.startActivity(intent);
                            finalNewGame.dismiss();
                        } else {
                            Toast.makeText(context, "Bitte Spielerzahl eingeben", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        newGame.show();
    }

    public androidx.appcompat.app.AlertDialog getDeleteWarning(final Context context) {

        final androidx.appcompat.app.AlertDialog warning_clear = new androidx.appcompat.app.AlertDialog.Builder(context).create();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        LayoutInflater inflater = LayoutInflater.from(context);
        View popUp = inflater.inflate(R.layout.warning_game, null);
        CheckBox check = popUp.findViewById(R.id.checkBox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    editor.putBoolean("check_warning", false);
                    Log.d("warning", "onCheckedChanged: warnings disabled");
                    editor.apply();
                }
                else {
                    editor.putBoolean("check_warning", true);
                    Log.d("warning", "onCheckedChanged: warnings enabled");
                    editor.apply();
                }
            }
        });
        warning_clear.setTitle("Spieldaten löschen");
        warning_clear.setMessage("Bist du sicher, dass du alle gespeicherten Spiele löschen möchtest?");
        warning_clear.setView(popUp, 75, 0, 75, 0);
        warning_clear.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setBoolean(true);
                warning_clear.dismiss();
                Toast.makeText(context, "Spieldaten gelöscht", Toast.LENGTH_SHORT).show();
            }
        });
        warning_clear.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, "Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setBoolean(false);
                warning_clear.dismiss();
            }
        });
        return warning_clear;
    }
}
