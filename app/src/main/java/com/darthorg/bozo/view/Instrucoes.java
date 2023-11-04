package com.darthorg.bozo.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.darthorg.bozo.R;
import com.darthorg.bozo.manager.IntrucoesManager;

import java.util.Locale;

public class Instrucoes extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private ImageButton btnSkip, btnNext, btnAnterior;
    private TextView titulo;
    private IntrucoesManager intrucoesManager;

    TextToSpeech ttsobject;
    int result;

    TextView txtAudio;
    ImageButton audio, audioOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        intrucoesManager = new IntrucoesManager(this);
        if (!intrucoesManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }


        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_instrucoes);

        viewPager = (ViewPager) findViewById(R.id.view_pager_intrucoes);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots_instrucoes);
        btnSkip = (ImageButton) findViewById(R.id.btn_sair);
        btnNext = (ImageButton) findViewById(R.id.btn_proximo);
        btnAnterior = (ImageButton) findViewById(R.id.btn_anterior);
        titulo = (TextView) findViewById(R.id.tituloInstrucoes);


        txtAudio = (TextView) findViewById(R.id.txtAudio);
        audio = (ImageButton) findViewById(R.id.audio);
        audioOff = (ImageButton) findViewById(R.id.audio_off);


        ttsobject = new TextToSpeech(Instrucoes.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {


                    result = ttsobject.isLanguageAvailable(new Locale("spa"));

                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.recurso_nao_suportado,
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                    Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                } else {
                    ttsobject.speak(getString(R.string.txtComojogarLer), TextToSpeech.QUEUE_FLUSH, null);
                    audioOff.setVisibility(View.VISIBLE);
                    audio.setVisibility(View.GONE);
                    txtAudio.setText(R.string.parar);
                }
            }
        });

        audioOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStop();
            }
        });

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.instrucoes_slide1,
                R.layout.instrucoes_slide2,
                R.layout.instrucoes_slide3,
                R.layout.instrucoes_slide4,
                R.layout.instrucoes_slide5,
                R.layout.instrucoes_slide6,
                R.layout.instrucoes_slide7,
                R.layout.instrucoes_slide8,
                R.layout.instrucoes_slide9,
                R.layout.instrucoes_slide10,
                R.layout.instrucoes_slide11,};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(-1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ttsobject != null) {

            audioOff.setVisibility(View.GONE);
            audio.setVisibility(View.VISIBLE);
            txtAudio.setText(" ");
            ttsobject.stop();
            ttsobject.shutdown();

        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_instrucoes_dot_ativo);
        int[] colorsInactive = getResources().getIntArray(R.array.array_instrucoes_dot_inativo);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        intrucoesManager.setFirstTimeLaunch(true);
        finish();
    }

    private void btnProximo() {
        btnNext.setImageDrawable(getResources().getDrawable(R.drawable.ic_proximo));
        btnSkip.setVisibility(View.VISIBLE);
    }

    private void btnStop() {
        if (ttsobject != null) {

            audioOff.setVisibility(View.GONE);
            audio.setVisibility(View.VISIBLE);
            txtAudio.setText(" ");
            ttsobject.stop();

        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);


            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                titulo.setText(getString(R.string.general));
                btnNext.setImageDrawable(getResources().getDrawable(R.drawable.ic_sair));
                btnSkip.setVisibility(View.GONE);
                btnAnterior.setVisibility(View.VISIBLE);

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.lerGeneral), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else if (position == layouts.length - 2) {

                titulo.setText(getString(R.string.quadrada));
                btnProximo();
                btnAnterior.setVisibility(View.VISIBLE);

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.lerQuadrada), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else if (position == layouts.length - 3) {

                titulo.setText(getString(R.string.seguida));
                btnProximo();
                btnAnterior.setVisibility(View.VISIBLE);

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.lerSeguida), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else if (position == layouts.length - 4) {

                titulo.setText(getString(R.string.full));
                btnProximo();
                btnAnterior.setVisibility(View.VISIBLE);

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.lerFull), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else if (position == layouts.length - 5) {

                titulo.setText(getString(R.string.sena));
                btnProximo();
                btnAnterior.setVisibility(View.VISIBLE);

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.lerSena), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else if (position == layouts.length - 6) {

                titulo.setText(getString(R.string.quina));
                btnProximo();
                btnAnterior.setVisibility(View.VISIBLE);

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.lerQuina), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else if (position == layouts.length - 7) {

                titulo.setText(getString(R.string.quadra));
                btnProximo();
                btnAnterior.setVisibility(View.VISIBLE);

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.lerQuadra), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else if (position == layouts.length - 8) {

                titulo.setText(getString(R.string.terno));
                btnProximo();
                btnAnterior.setVisibility(View.VISIBLE);

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.lerTerno), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else if (position == layouts.length - 9) {

                titulo.setText(getString(R.string.duque));
                btnProximo();
                btnAnterior.setVisibility(View.VISIBLE);

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.lerDuque), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else if (position == layouts.length - 10) {

                titulo.setText(getString(R.string.az));
                btnProximo();
                btnAnterior.setVisibility(View.VISIBLE);

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.lerAz), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else if (position == layouts.length - 11) {

                titulo.setText("");
                btnAnterior.setVisibility(View.GONE);
                btnProximo();

                audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getApplicationContext(), R.string.recurso_nao_suportado, Toast.LENGTH_SHORT).show();
                        } else {
                            ttsobject.speak(getString(R.string.txtComojogarLer), TextToSpeech.QUEUE_FLUSH, null);
                            audioOff.setVisibility(View.VISIBLE);
                            audio.setVisibility(View.GONE);
                            txtAudio.setText(R.string.parar);
                        }
                    }
                });

                audioOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnStop();
                    }
                });

            } else {
                // still pages are left

                btnNext.setImageDrawable(getResources().getDrawable(R.drawable.ic_proximo));
                btnSkip.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorBlackTransparente3));
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}