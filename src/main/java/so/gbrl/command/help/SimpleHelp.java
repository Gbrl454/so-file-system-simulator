package so.gbrl.command.help;

import so.gbrl.command.CommandBase;
import so.gbrl.exceptions.SoException;
import so.gbrl.utils.ReflectionUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.println;

public class SimpleHelp extends CommandBase {
    public SimpleHelp() {
        super("^help");
    }

    @Override
    public void run(Matcher matcher) {
        try {
            List<Class<?>> commandClasses = Objects.requireNonNull(ReflectionUtil.getCommandClasses(null)).stream().filter(it -> it != SimpleHelp.class && it != CommandBase.class).toList();

            println("Lista de comandos do sistema:");

            for (Class<?> clazz : commandClasses) {
                if (CommandBase.class.isAssignableFrom(clazz)) {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    String result = (String) clazz.getMethod("help").invoke(instance);
                    println("- " + clazz.getSimpleName() + ":\n-\t" + result);
                }
            }

        } catch (Exception e) {
            println(SoException.UNEXPECTED_ERROR);
        }
    }

    @Override
    public String help() {
        return "TODO";
    }
}