package rcp3project;

import org.eclipse.core.commands.Command;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "rcp3Project"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		UIJob job = new UIJob("InitCommandsWorkaround") {

			@Override
			public IStatus runInUIThread(@SuppressWarnings("unused") IProgressMonitor monitor) {

				ICommandService commandService = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getService(ICommandService.class);
				Command command = commandService.getCommand("ExpandAction.radio");
				command.isEnabled();
				return new Status(IStatus.OK, "my.plugin.id", "Init commands workaround performed succesfully");
			}

		};
		job.schedule();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
