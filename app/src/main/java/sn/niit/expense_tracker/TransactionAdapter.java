package sn.niit.expense_tracker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sn.niit.expense_tracker.domain.Transaction;
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private final List<Transaction> transactionList;

    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.tvTitle.setText(transaction.getDescription());
        holder.tvSubtitle.setText(transaction.getCategory());
        holder.tvAmount.setText(String.format("%.2f", transaction.getTotal()) + " FCFA");

        // Optionally, you can differentiate the display based on transaction type (e.g., color coding)
        if (transaction.getType() == Transaction.TransactionType.EXPENSE) {
            holder.tvAmount.setTextColor(Color.RED);
        } else if (transaction.getType() == Transaction.TransactionType.INCOME) {
            holder.tvAmount.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle, tvAmount;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
