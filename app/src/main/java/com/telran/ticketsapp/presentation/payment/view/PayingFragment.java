package com.telran.ticketsapp.presentation.payment.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.databinding.FragmentPayingBinding;
import com.telran.ticketsapp.presentation.payment.presenter.PaymentPresenter;

import java.math.BigDecimal;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayingFragment extends MvpAppCompatFragment implements PayingView {
    FragmentPayingBinding binding;
    PayPalConfiguration config;
    private static final int PAYPAL = 100;
    public static final String PAYPAL_CLIENT_ID = "AddQsOOi3DJvnX69nW7YqKBRk2zLIr-uETyrA1NeKDbLd84wODp2YBnXHQIaaR1T2sZCG2y34-cKvlY5";
    private static final String TAG = "PayingFragment";
//    @InjectPresenter
    PaymentPresenter presenter;

    public PayingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO inhect presenter
        presenter = new PaymentPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPayingBinding.inflate(inflater,container,false);
        Bundle bundle = getArguments();
        binding.eventInfo.setText(bundle.getString("eventInfo"));
        int count = bundle.getInt("ticketsQty");
        float total = bundle.getFloat("ticketsSum");
        String qty = getResources().getQuantityString(R.plurals.tickets_qty,count,count);
        binding.ticketsTotalTxt.setText(qty);
        binding.ticketsSumTxt.setText(getString(R.string.price_range,total));
        binding.selectPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                unblockPayBtn(isChecked);
            }
        });
        binding.payBtn.setOnClickListener(v -> {
            makePayment(total);
        });
        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(PAYPAL_CLIENT_ID)
                .defaultUserEmail("sb-e74dh1786940@personal.example.com")
                .forceDefaultsOnSandbox(true)
                .sandboxUserPassword("mAS/1w'X");

        return binding.getRoot();
    }

    private void makePayment(float total) {
        Intent intent = new Intent(requireContext(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        getActivity().startService(intent);

        PayPalPayment payment = new PayPalPayment(new BigDecimal(total),"EUR","Payment",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent paymentIntent = new Intent(requireContext(), PaymentActivity.class);
        paymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
        paymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startActivityForResult(paymentIntent,PAYPAL);
    }

    @Override
    public void unblockPayBtn(boolean isEnabled) {
        binding.payBtn.setEnabled(isEnabled);
    }

    @Override
    public void showNextView(Bundle bundle) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_payingFragment_to_successFragment);
    }

    @Override
    public void showSuccess() {
        Log.d(TAG, "showSuccess: showing");
        binding.paySuccess.setVisibility(View.VISIBLE);
        binding.payBtn.setEnabled(false);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_payingFragment_to_successFragment);
            }
        },3000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL){
            if (resultCode == RESULT_OK){
                Log.d(TAG, "onActivityResult: result ok");
              //  presenter.onPaymentSuccess();
                showSuccess();
            }
        }

    }
}
