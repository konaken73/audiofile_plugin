package com.kencorp.music.audiofileplugin;

import android.annotation.TargetApi;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.Build;

import java.util.ArrayList;


public class AudioVisualizer {

    public static final AudioVisualizer instance = new AudioVisualizer();

    private static final String TAG = "AudioVisualizer";

    private Visualizer visualizer;

    private  Equalizer equalizer;
    public boolean isActive() {
        return visualizer != null;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void activate(Visualizer.OnDataCaptureListener listener) {


        visualizer = new Visualizer(AudiofilePlugin.playerId);

        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        visualizer.setDataCaptureListener(
                listener,
                Visualizer.getMaxCaptureRate() / 2,
                false,
                true
        );
        visualizer.setEnabled(true);
/*
         equalizer = new Equalizer(0, MusicFinderPlugin.playerId);
        equalizer.setEnabled(true);

        setupEqualizerFxAndUI();*/
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void deactivate() {
        visualizer.release();
        visualizer = null;
    }

    public ArrayList<Short> setEqualizer()
    {
        ArrayList<Short> tab = new ArrayList<Short>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            equalizer = new Equalizer(0, AudiofilePlugin.playerId);

            equalizer.setEnabled(true);


            // get number frequency bands supported by the equalizer

            short numberFrequencyBands = equalizer.getNumberOfBands();


            // get the level ranges to be used in settings the bands
            // get  lower limit of  the range in milliBels

            final short lowerEqualizerBandLevel = equalizer.getBandLevelRange()[0];
            // get  Upper limit of the range in milliBels

            final short upperEqualizerBandLevel = equalizer.getBandLevelRange()[1];
            tab.add(0,numberFrequencyBands);
            tab.add(1,lowerEqualizerBandLevel);
            tab.add(2,upperEqualizerBandLevel);


        }
        return tab;
       // setupEqualizerFxAndUI();
    }
/*
    void setupEqualizerFxAndUI()
    {


        // get number frequency bands supported by the equalizer

        short numberFrequencyBands = equalizer.getNumberOfBands();

        // get the level ranges to be used in settings the bands
        // get  lower limit of  the range in milliBels

        final short lowerEqualizerBandLevel = equalizer.getBandLevelRange()[0];
        // get  Upper limit of the range in milliBels

        final short upperEqualizerBandLevel = equalizer.getBandLevelRange()[1];

        // loop through all the equalizer bands  to display the lowest and the uppest levels
        // and the seek bars

        for(short i=0;i<numberFrequencyBands;i++)
        {
            final short equalizerBandIndex =i;
             // frequency header for each seekBar
          /*
            TextView frequencyHeaderTextview = new TextView(this);
            frequencyHeaderTextview.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            frequencyHeaderTextview.setGravity(Gravity.CENTER_HORIZONTAL);

            frequencyHeaderTextview.setText((equalizer.getCenterFreq(equalizerBandIndex)/1000)+"Mhz");

            linearLayout.addView(frequencyHeaderTextview);

            // set up  linear layout to contain each seekBar

            LinearLayout seekBarRowLayout = new LinearLayout(this);

            seekBarRowLayout.setOrientation(LinearLayout.HORIZONTAL);

            // set up lower level textview for this seekbar

            TextView lowerEqualizerBandLevelTextView = new TextView(this);

            lowerEqualizerBandLevelTextView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


            lowerEqualizerBandLevelTextView.setText(-(lowerEqualizerBandLevel/100)+"dB");

            // set up upper level textview for this seekbar

            TextView upperEqualizerBandLevelTextView = new TextView(this);

            upperEqualizerBandLevelTextView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            upperEqualizerBandLevelTextView.setText((upperEqualizerBandLevel/100)+"dB");


            // ------- seekbar ------

            // set the layout parameters for the seekbar

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.weight=1;

            // create a new seekBar
            SeekBar seekBar = new SeekBar(this);

            // give the seekBar an ID

            seekBar.setId(i);
            seekBar.setLayoutParams(layoutParams);

            seekBar.setMax(upperEqualizerBandLevel-lowerEqualizerBandLevel);

            // set the progress for the seek bar
            seekBar.setProgress(equalizer.getBandLevel(equalizerBandIndex));

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    equalizer.setBandLevel(equalizerBandIndex,(short)(progress + lowerEqualizerBandLevel));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            // add the lower and the upper band level textviews and the

            seekBarRowLayout.addView(lowerEqualizerBandLevelTextView);
            seekBarRowLayout.addView(seekBar);
            seekBarRowLayout.addView(upperEqualizerBandLevelTextView);

            linearLayout.addView(seekBarRowLayout);

            // show the spinner

            equalizeSound();
        }


    }

    void equalizeSound()
    {

        // set up the spinner
/*
        ArrayList<String> equalizerPresetNames = new ArrayList<String>();

        ArrayAdapter<String> equalizerPresetSpinnerAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,equalizerPresetNames);

*/
  //      equalizerPresetSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    //    Spinner equalizerPresetSpinner = (Spinner) findViewById(R.id.spinner);

/*
        for(short i=0;i<equalizer.getNumberOfPresets();i++)
        {
            equalizerPresetNames.add(equalizer.getPresetName(i));
        }

      //  equalizerPresetSpinner.setAdapter(equalizerPresetSpinnerAdapter);

        // handle the spinner item selections

        equalizerPresetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                equalizer.usePreset((short) position);
                short numberFrequencyBands = equalizer.getNumberOfBands();

                final short lowerEqualizerBandLevel = equalizer.getBandLevelRange()[0];

                for(short i=0;i<numberFrequencyBands;i++)
                {
                    short equalizerBandIndex = i;

                    SeekBar seekBar =(SeekBar) findViewById(equalizerBandIndex);

                    seekBar.setProgress(equalizer.getBandLevel(equalizerBandIndex)-lowerEqualizerBandLevel);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/

}
