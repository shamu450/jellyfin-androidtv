package org.jellyfin.androidtv.ui.playback.overlay.action

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import org.jellyfin.androidtv.R
import org.jellyfin.androidtv.ui.playback.PlaybackController
import org.jellyfin.androidtv.ui.playback.overlay.CustomPlaybackTransportControlGlue
import org.jellyfin.androidtv.ui.playback.overlay.VideoPlayerAdapter
import org.jellyfin.androidtv.util.popupMenu

class RepeatAction(
	context: Context,
	customPlaybackTransportControlGlue: CustomPlaybackTransportControlGlue,
) : CustomAction(context, customPlaybackTransportControlGlue) {
	private var popup: PopupMenu? = null

	init {
		initializeWithIcon(R.drawable.ic_loop)
	}

	override fun handleClickAction(
		playbackController: PlaybackController,
		videoPlayerAdapter: VideoPlayerAdapter,
		context: Context,
		view: View,
	) {
		videoPlayerAdapter.leanbackOverlayFragment.setFading(false)
		dismissPopup()
		popup = popupMenu(context, view, Gravity.END) {
			item(context.getString(R.string.lbl_repeat_off)) {
				playbackController.repeatMode = PlaybackController.REPEAT_OFF
			}.apply {
				isChecked = playbackController.repeatMode == PlaybackController.REPEAT_OFF
			}

			item(context.getString(R.string.lbl_repeat_one)) {
				playbackController.repeatMode = PlaybackController.REPEAT_ONE
			}.apply {
				isChecked = playbackController.repeatMode == PlaybackController.REPEAT_ONE
			}

			item(context.getString(R.string.lbl_repeat_all)) {
				playbackController.repeatMode = PlaybackController.REPEAT_ALL
			}.apply {
				isChecked = playbackController.repeatMode == PlaybackController.REPEAT_ALL
			}
		}
		popup?.menu?.setGroupCheckable(0, true, true)
		popup?.setOnDismissListener {
			videoPlayerAdapter.leanbackOverlayFragment.setFading(true)
			popup = null
		}
		popup?.show()
	}

	fun dismissPopup() {
		popup?.dismiss()
	}
}
