package seedu.address.logic.commands.tags;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ViewTagTreeCommand extends Command {

    public static final CommandWord commandWord = CommandWord.VIEWTREE;

    public static final CommandType commandType = CommandType.TAG;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Tag tree shown!", false, true, false);
    }
}
