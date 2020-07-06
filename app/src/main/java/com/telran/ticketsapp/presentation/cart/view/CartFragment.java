package com.telran.ticketsapp.presentation.cart.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.databinding.FragmentCartBinding;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;
import com.telran.ticketsapp.presentation.cart.presenter.CartPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends MvpAppCompatFragment implements CartView {
    FragmentCartBinding binding;

    @InjectPresenter
    CartPresenter presenter;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getTicketsInCart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater,container,false);
        binding.clearBtn.setOnClickListener(v -> presenter.deleteFromCart());
        binding.selectedSeats.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));
        binding.selectedSeats.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.payBtn.setOnClickListener(v ->
                presenter.proceedToPayment());
        return binding.getRoot();
    }

    @Override
    public void setData(String eventInfo, double total, CartAdapter adapter) {
        binding.eventInfo.setText(eventInfo);
        binding.selectedSeats.setAdapter(adapter);
        int count = adapter.getItemCount();
        String qty = getResources().getQuantityString(R.plurals.tickets_qty,count,count);
        binding.ticketsTotalTxt.setText(qty);
        binding.ticketsSumTxt.setText(getString(R.string.price_range,total));
        binding.emptyCartTxt.setVisibility(View.GONE);
        binding.cartNotEmpty.setVisibility(View.VISIBLE);

    }

    @Override
    public void clearCart() {
        binding.emptyCartTxt.setVisibility(View.VISIBLE);
        binding.cartNotEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showNextView(Bundle bundle) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cartFragment_to_payingFragment,bundle);
    }
}
