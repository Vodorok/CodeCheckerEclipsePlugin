package org.codechecker.eclipse.plugin.codechecker.locator;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.codechecker.eclipse.plugin.codechecker.ICodeChecker;
import org.codechecker.eclipse.plugin.codechecker.ICodeCheckerFactory;
import org.codechecker.eclipse.plugin.runtime.IShellExecutorHelperFactory;
import org.codechecker.eclipse.plugin.runtime.ShellExecutorHelper;

/**
 * Provides a CodeChecker instance tied to a CodeChecker package resulting of
 * "which CodeChecker".
 */
public class EnvCodeCheckerLocatorService extends CodeCheckerLocatorService {
    public static final String ERROR = "CodeChecker wasn't found in PATH environment variable!";

    @Override
    public ICodeChecker findCodeChecker(Path path, Path pathToVenv,
            ICodeCheckerFactory ccFactory, IShellExecutorHelperFactory sheFactory) throws InvalidCodeCheckerException {

        ShellExecutorHelper she = sheFactory.createShellExecutorHelper(System.getenv());
        String location = she.quickReturnFirstLine("which CodeChecker").or("");
        try {
            return ccFactory.createCodeChecker(Paths.get(location), she);
        } catch (InvalidCodeCheckerException e) {
            throw new InvalidCodeCheckerException(ERROR);
        }
    }
}
