package com.tumblr.example.binderlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import com.tumblr.example.PrimitiveAdapter;
import com.tumblr.example.binder.ColorNameToastBinder;
import com.tumblr.example.binder.TextPrimitiveBinder;
import com.tumblr.example.dagger.PerActivity;
import com.tumblr.example.model.ColorNamePrimitive;
import com.tumblr.example.viewholder.PrimitiveViewHolder;
import com.tumblr.graywater.GraywaterAdapter;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericleong on 3/28/16.
 */
@PerActivity
public class ColorNamePrimitiveItemBinder
		implements GraywaterAdapter.ItemBinder<ColorNamePrimitive, PrimitiveViewHolder,
		GraywaterAdapter.Binder<ColorNamePrimitive, PrimitiveViewHolder, ? extends PrimitiveViewHolder>>,
		GraywaterAdapter.ActionListener<ColorNamePrimitive, PrimitiveViewHolder, PrimitiveViewHolder> {

	private final Provider<TextPrimitiveBinder<ColorNamePrimitive>> mColorNameTextBinder;
	private final Provider<ColorNameToastBinder> mColorNameToastBinder;

	private final Provider<PrimitiveAdapter> mAdapter;

	@Inject
	public ColorNamePrimitiveItemBinder(final Provider<PrimitiveAdapter> adapter,
	                                    final Provider<TextPrimitiveBinder<ColorNamePrimitive>> colorNameTextBinder,
	                                    final Provider<ColorNameToastBinder> colorNameToastBinder) {
		mColorNameTextBinder = colorNameTextBinder;
		mColorNameToastBinder = colorNameToastBinder;
		mAdapter = adapter;
	}

	@NonNull
	@Override
	public List<Provider<? extends GraywaterAdapter.Binder<ColorNamePrimitive, PrimitiveViewHolder, ? extends PrimitiveViewHolder>>>
	getBinderList(@NonNull final ColorNamePrimitive model, final int position) {
		return new ArrayList<Provider<
				? extends GraywaterAdapter.Binder<ColorNamePrimitive, PrimitiveViewHolder, ? extends PrimitiveViewHolder>>>() {{
			add(mColorNameTextBinder);
			add(mColorNameToastBinder);
		}};
	}

	@Override
	public void act(@NonNull final ColorNamePrimitive model,
	                @NonNull final PrimitiveViewHolder holder,
	                @NonNull final View v,
	                @NonNull final List<Provider<GraywaterAdapter.Binder<
			                ? super ColorNamePrimitive, PrimitiveViewHolder, ? extends PrimitiveViewHolder>>> binderList,
	                final int binderIndex,
	                @Nullable final Object obj) {
		Toast.makeText(v.getContext(), model.getString(), Toast.LENGTH_SHORT).show();

		final PrimitiveAdapter adapter = mAdapter.get();

		adapter.add(adapter.getItemPosition(holder.getAdapterPosition()) + 1,
				new ColorNamePrimitive(model.getColor(), model.getString() + "+"), true);
	}
}
