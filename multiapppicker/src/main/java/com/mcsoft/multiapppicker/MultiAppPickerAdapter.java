package com.mcsoft.multiapppicker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.l4digital.fastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

class MultiAppPickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FastScroller.SectionIndexer, Filterable {

    private List<Application> applicationItemList;
    private List<Application> applicationItemListOriginal;
    private ApplicationSelectListener listener;
    private MultiAppPicker.Builder builder;
    private int selectedAlreadyAnItem = -1;
    ApplicationViewHolder applicationViewHolder = null;
    interface ApplicationSelectListener{
        void onApplicationSelected(Application application, int totalSelectedApplication);
    }

    MultiAppPickerAdapter(List<Application> applicationItemList, ApplicationSelectListener listener, MultiAppPicker.Builder builder) {
        this.applicationItemList = applicationItemList;
        this.applicationItemListOriginal = applicationItemList;
        this.listener = listener;
        this.builder = builder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(com.mcsoft.multiapppicker.R.layout.list_row_app_pick_item, viewGroup, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {

        if(holder instanceof ApplicationViewHolder) {
            applicationViewHolder = (ApplicationViewHolder) holder;
            final Application applicationItem = getItem(i);
            applicationViewHolder.tvApplicationName.setText(applicationItem.getApplicationTitle());
            applicationViewHolder.tvApplicationPackageName.setText(applicationItem.getApplicationPackageName());
            applicationViewHolder.vRoundLetterView.setBackground(applicationItem.getApplicationImage());
            applicationViewHolder.mView.setTag(i);
            if (builder.selectOnlyOneItem == true) {
                if (selectedAlreadyAnItem == i) {
                    applicationViewHolder.ivSelectedState.setVisibility(View.VISIBLE);
                    applicationViewHolder.vRoundLetterView.setBackground(null);

                } else {
                    applicationViewHolder.ivSelectedState.setVisibility(View.INVISIBLE);
                    applicationViewHolder.vRoundLetterView.setBackground(applicationItem.getApplicationImage());
                }
            } else {
                if (applicationItem.isSelected()) {
                    applicationViewHolder.ivSelectedState.setVisibility(View.VISIBLE);
                    applicationViewHolder.vRoundLetterView.setBackground(null);

                } else {
                    applicationViewHolder.ivSelectedState.setVisibility(View.INVISIBLE);
                    applicationViewHolder.vRoundLetterView.setBackground(applicationItem.getApplicationImage());
                }
            }

            applicationViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        // Check added by MrJack
                        if (builder.selectOnlyOneItem == false) {
                            setApplicationSelected(applicationItem.getId());
                            listener.onApplicationSelected(getItem(i), getSelectedApplicationsCount());
                        } else {
                            // Added by MrJack
                            selectedAlreadyAnItem = i;
                            applicationItem.setSelected(false);
                            setApplicationSelected(applicationItem.getId());
                            listener.onApplicationSelected(applicationItemList.get(i), 1);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    private void setApplicationSelected(long id){
            int pos = getItemPosition(applicationItemList, id);
            applicationItemList.get(pos).setSelected(!applicationItemList.get(pos).isSelected());
    }

    private int getItemPosition(List<Application> list, long id){
        int i = 0;
        for(Application application : list) {
            if(application.getId() == id) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private int getSelectedApplicationsCount() {
        return getSelectedApplications().size();
    }

    List<Application> getSelectedApplications() {
        List<Application> selectedApplications = new ArrayList<>();
        for(Application application : applicationItemListOriginal){
            if(application.isSelected()){
                selectedApplications.add(application);
            }
            else {
                selectedApplications.remove(application);
            }
        }
        return selectedApplications;
    }

    @Override
    public int getItemCount() {
        return (null != applicationItemList ? applicationItemList.size() : 0);
    }

    private Application getItem(int pos){
        return applicationItemList.get(pos);
    }

    @Override
    public String getSectionText(int position) {
        try {
            return String.valueOf(applicationItemList.get(position).getApplicationTitle().charAt(0));
        } catch (NullPointerException | IndexOutOfBoundsException ex){
            ex.printStackTrace();
            return "";
        }
    }

    private class ApplicationViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private TextView tvApplicationName;
        private TextView tvApplicationPackageName;
        private ImageView vRoundLetterView;
        private ImageView ivSelectedState;
        ApplicationViewHolder(View view) {
            super(view);
            this.mView = view;
            this.vRoundLetterView = (ImageView) view.findViewById(com.mcsoft.multiapppicker.R.id.vRoundLetterView);
            this.tvApplicationName = (TextView) view.findViewById(com.mcsoft.multiapppicker.R.id.tvApplicationName);
            this.tvApplicationPackageName = (TextView) view.findViewById(com.mcsoft.multiapppicker.R.id.tvApplicationPackageName);
            this.ivSelectedState = (ImageView) view.findViewById(com.mcsoft.multiapppicker.R.id.ivSelectedState);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                applicationItemList = (List<Application>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Application> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = applicationItemListOriginal;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }
                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }
        };
    }

    private List<Application> getFilteredResults(String constraint) {
        List<Application> results = new ArrayList<>();
        for (Application item : applicationItemListOriginal) {
            if (item.getApplicationTitle().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

}