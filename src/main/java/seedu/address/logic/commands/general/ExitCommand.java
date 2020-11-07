package seedu.address.logic.commands.general;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.EXIT.toString();

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Athena as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false, true);
    }

}
