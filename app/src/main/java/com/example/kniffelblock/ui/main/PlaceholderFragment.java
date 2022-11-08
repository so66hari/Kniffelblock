package com.example.kniffelblock.ui.main;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;

import com.example.kniffelblock.ButtonDialogs;
import com.example.kniffelblock.GameDao;
import com.example.kniffelblock.GameRoomDatabase;
import com.example.kniffelblock.PointCalculator;
import com.example.kniffelblock.R;
import com.example.kniffelblock.Stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PlaceholderFragment extends Fragment {

    private static String[] row_titles = {"Einser", "Zweier", "Dreier", "Vierer", "Fünfer", "Sechser",
            "Bonus", "1. Zwischensumme", "", "Dreierpasch", "Viererpasch", "Full House", "Kl. Straße", "Gr. Straße", "Kniffel", "Chance", "2. Zwischensumme", "Gesamtpunktzahl"};

    private int column;
    private TableLayout table;
    private TableRow row;
    private ArrayList<ArrayList<Integer>> pointArray;
    private ArrayList<Integer> playerNumArray;
    private ArrayList<Integer> rowArray;
    private int index;
    private boolean continueGame;
    private ArrayList<ArrayList<EditText>> editTexts;

    private FragmentToActivity mCallback;

    //BUILD
    static PlaceholderFragment newInstance(int index, Boolean continueButton) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("continue", continueButton);
        bundle.putInt("player", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    //INTERFACE
    public interface FragmentToActivity {
        void communicate(ArrayList<ArrayList<Integer>> points);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    private void sendData(ArrayList<ArrayList<Integer>> comm) {
        mCallback.communicate(comm);
    }

    //FRAGMENT
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt("player", 1);
        Log.i(TAG, "onCreate: index: " + index);
        continueGame = getArguments().getBoolean("continue", false);
        pointArray = new ArrayList<>();
        playerNumArray = new ArrayList<>();
        rowArray = new ArrayList<>();
        editTexts = new ArrayList<>();
        playerNumArray.add(0, 0);
        pointArray.add(0, playerNumArray);
        editTexts.add(0, null);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        //views
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        //getArguments().getInt("player", 0);
        table = root.findViewById(R.id.table1);

        //BUTTONLABEL
        int j = 0;
        for (int i = 1; i < table.getChildCount(); i++) {
            row = (TableRow) table.getChildAt(i);
            Button button = (Button) row.getChildAt(0);
            button.setText(row_titles[j]);
            j++;
        }

        //BUTTONS
        for (int i = 1; i < 16; i++) {
            int id = getResources().getIdentifier("button_" + i, "id", getActivity().getPackageName());
            final Button buttons = root.findViewById(id);

            buttons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ButtonDialogs open = new ButtonDialogs(getContext());
                    open.getDialog(buttons).show();
                }
            });
        }

        //button for addRound
        Button add_round = root.findViewById(R.id.button_add);
        add_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRound();
            }
        });

        column = 0;
        if(continueGame) {
            new ContinueGame().execute(index);
        }
        else {
            addRound();
        }
        return root;
    }

    private ArrayList<Integer> fillEmptyArray() {
        return new ArrayList<>(Collections.nCopies(17, 0));
    }

    private void setEditTexts(final int pos, ArrayList<Integer> mRowArray) {
        ArrayList<EditText> list = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            row = (TableRow) table.getChildAt(i);
            EditText newEdit = new EditText(this.getContext());
            String tag = "edit_" + pos + i;
            newEdit.setTag(tag);
            if(!mRowArray.get(i-1).toString().equals("0")) {
                String text = mRowArray.get(i-1).toString();
                newEdit.setText(text);
            }
            list.add(newEdit);
            row.addView(newEdit, pos);
        }

        for (int i = 10; i <= 16; i++) {
            row = (TableRow) table.getChildAt(i);
            EditText newEdit = new EditText(this.getContext());
            String tag = "edit_" + pos + i;
            newEdit.setTag(tag);
            if(!mRowArray.get(i-2).toString().equals("0")) {
                String text = mRowArray.get(i-2).toString();
                newEdit.setText(text);
            }
            list.add(newEdit);
            row.addView(newEdit, column);
        }

        for (EditText editTexts : list) {
            editTexts.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTexts.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                editTexts.setTextAppearance(R.style.column_edits);
            } else {
                editTexts.setTextAppearance(getContext(), R.style.column_edits);
            }
            final String editTextsTag = editTexts.getTag().toString();

            //adds points dynamically if editTexts lose focus
            View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, final boolean b) {
                    if (view.getTag().toString().equals(editTextsTag) && !b) {
                        calculatePoints(pos);
                        sendData(pointArray);
                    }
                }
            };
            editTexts.setOnFocusChangeListener(listener);
        }
        editTexts.add(pos, list);

    }

    private ArrayList<EditText> getEditTexts(int pos) {
        return editTexts.get(pos);
    }

    private void addRound() {
        column++;
        ArrayList<Integer> mRowArray;

        playerNumArray.set(0, column);
        pointArray.set(0, playerNumArray);
        try {
            mRowArray = pointArray.get(column);
        } catch (IndexOutOfBoundsException i) {
            mRowArray = fillEmptyArray();
            pointArray.add(column, mRowArray);
        }
        //header
        TableRow row = (TableRow) table.getChildAt(0);
        TextView label = new TextView(this.getContext());
        String label_text = "Runde " + column;
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.M) {
            label.setTextAppearance(R.style.TextAppearance_AppCompat_Widget_Button_Borderless_Colored);
        } else {
            label.setTextAppearance(getContext(), R.style.TextAppearance_AppCompat_Widget_Button_Borderless_Colored);
        }
        label.setPadding(40, 0, 40, 0);
        label.setText(label_text);

        row.addView(label, column);

        //bonus Row
        row = (TableRow) table.getChildAt(7);
        TextView bonus = new TextView(this.getContext());
        bonus.setGravity(Gravity.CENTER);
        bonus.setTextSize(18);
        if(!mRowArray.get(6).toString().equals("0")) {
            String text = mRowArray.get(6).toString();
            bonus.setText(text);
        }
        row.addView(bonus, column);

        //sum1Row
        row = (TableRow) table.getChildAt(8);
        TextView sum1 = new TextView(this.getContext());
        sum1.setGravity(Gravity.CENTER);
        sum1.setTextSize(18);
        sum1.setTextColor(getResources().getColor(R.color.white));
        if(!mRowArray.get(7).toString().equals("0")) {
            String text = mRowArray.get(7).toString();
            sum1.setText(text);
        }
        row.addView(sum1, column);

        //placeholder
        row = (TableRow) table.getChildAt(9);
        Placeholder empty = new Placeholder(this.getContext());
        row.addView(empty, column);

        //sum2
        row = (TableRow) table.getChildAt(17);
        TextView sum2 = new TextView(this.getContext());
        sum2.setGravity(Gravity.CENTER);
        sum2.setTextSize(18);
        sum2.setTextColor(getResources().getColor(R.color.white));
        if(!mRowArray.get(15).toString().equals("0")) {
            String text = mRowArray.get(15).toString();
            sum2.setText(text);
        }
        row.addView(sum2, column);

        //totalsum
        row = (TableRow) table.getChildAt(18);
        TextView sum = new TextView(this.getContext());
        sum.setTextSize(18);
        sum.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        sum.setTextColor(getResources().getColor(R.color.white));
        sum.setGravity(Gravity.CENTER);
        if(!mRowArray.get(16).toString().equals("0")) {
            String text = mRowArray.get(16).toString();
            sum.setText(text);
        }
        row.addView(sum, column);

        setEditTexts(column, mRowArray);
    }

    private void calculatePoints(int pos) {

        TableRow row;
        TextView view;

        String fill;
        ArrayList<String> list = new ArrayList<>();
        for (EditText editText : getEditTexts(pos)) {
            if(editText.getText().toString().isEmpty()) {
                list.add("0");
            } else {
                list.add(editText.getText().toString());
            }
        }

        PointCalculator calculator = new PointCalculator(list);
        rowArray = calculator.addPoints();
        pointArray.set(pos, rowArray);

        fill = Integer.toString(rowArray.get(6));
        row = (TableRow) table.getChildAt(7);
        view = (TextView) row.getChildAt(pos);
        if(!fill.equals("0")) {
            view.setText(fill);
        }
        else view.setText("");

        fill = Integer.toString(rowArray.get(7));
        row = (TableRow) table.getChildAt(8);
        view = (TextView) row.getChildAt(pos);
        if(!fill.equals("0")) {
            view.setText(fill);
        } else view.setText("");

        fill = Integer.toString(rowArray.get(15));
        row = (TableRow) table.getChildAt(17);
        view = (TextView) row.getChildAt(pos);
        if(!fill.equals("0")) {
            view.setText(fill);
        }
        else view.setText("");

        fill = Integer.toString(rowArray.get(16));
        row = (TableRow) table.getChildAt(18);
        view = (TextView) row.getChildAt(pos);
        if(!fill.equals("0")) {
            view.setText(fill);
        } else view.setText("");
    }

    private class ContinueGame extends AsyncTask<Integer, Void, List<Stats>> {
        List<Stats> gameList;
        GameRoomDatabase database = GameRoomDatabase.getDatabase(getContext());
        GameDao gameDao = database.gameDao();

        @Override
        protected List<Stats> doInBackground(Integer... params) {
            gameList = gameDao.getLastPlayerStats(params[0]);
            return gameList;
        }

        @Override
        protected void onPostExecute(List<Stats> game) {
            fillStuff(gameList);
        }
    }

    private void fillStuff(List<Stats> game) {

        for (int i = 0; i < game.size(); i++) {
            //get point list
            Stats oneGame = game.get(i);
            String points = oneGame.getPoints();
            ArrayList<String> rowArrayAsString = new ArrayList<>(Arrays.asList(points.split("\t")));
            rowArray = new ArrayList<>();
            for(String point : rowArrayAsString) {
                rowArray.add(Integer.parseInt(point.trim()));
            }
            //add pointList to pointArray
            pointArray.add(i+1, rowArray);
            addRound();
        }
    }
}