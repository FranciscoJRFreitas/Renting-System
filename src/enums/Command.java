package enums;

/**
 * <code>Enum</code> with all the user s commands in the application.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public enum Command {

	REGISTER("register"), USERS("users"), PROPERTY("property"), PROPERTIES("properties"), BOOK("book"),
	CONFIRM("confirm"), REJECT("reject"), REJECTIONS("rejections"), PAY("pay"), REVIEW("review"), GUEST("guest"),
	STAYS("stays"), SEARCH("search"), BEST("best"), GLOBETROTTER("globetrotter"), HELP("help"), EXIT("exit"),
	UNKNOWN_COMMAND("Unknown command. Type help to see available commands."), BYE("Bye!");

	private final String commandInfo;

	private Command(String commandInfo) {
		this.commandInfo = commandInfo;
	}

	public String getCommandInfo() {
		return commandInfo;
	}

}
