/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.oreplay.library.messages;

/**
 * Class that contains the messages used by the app
 * <p>
 * The messages are managed as resources, to ease the internationalization of the apps
 * </p>
 * @author jarufe
 * @version 1.0
 *
 */
public class Messages extends java.util.ListResourceBundle {
    static final Object[][] contents = {
        {"oreplay", "OReplay"},
        {"main", "OReplay. Desktop Client 0.3.7"},
        {"version", "v0.3.7"},
        {"about_description", "OReplay is a free web service that aims to provide a modern, user-friendly and open source hub to all orienteering events.<BR>Live and final results can be uploaded to be displayed during and after the event."},
        {"about_github", "<a href='https://github.com/oreplay' target='_blank'>https://github.com/oreplay</a>"},
        {"about_development", "OReplay Development Team"},
        {"about_developers_title", "List of developers, alphabetical order"},
        {"about_developers_list", "Javier Arufe, Andreu Espinosa, Sergio García, Manuel Pedre, Jessica Penela, Adrián Pérez, Amador Real, Guillem Serrallonga"},
        {"file", "File"},
        {"save_conf", "Save Configuration"},
        {"exit", "Exit"},
        {"help", "Help"},
        {"configuration", "Configuration"},
        {"online_manual", "Online Manual"},
        {"check_updates", "Check for Updates"},
        {"event", "Event"},
        {"server", "Server"},
        {"server_value", "www.oreplay.es"},
        {"runners", "Runners"},
        {"runners_list", "List of runners"},
        {"follow_up", "Follow up"},
        {"global_panel", "Global panel"},
        {"server_name", "Server name"},
        {"ip_address", "IP address"},
        {"port", "Port"},
        {"database", "Database"},
        {"username", "Username"},
        {"password", "Password"},
        {"accept", "Accept"},
        {"cancel", "Cancel"},
        {"code", "Code"},
        {"description", "Description"},
        {"date", "Date"},
        {"number", "Number"},
        {"width", "Width"},
        {"height", "Height"},
        {"control", "Control"},
        {"file_import", "File to import"},
        {"browse", "Browse..."},
        {"initial_time", "Initial time"},
        {"bib", "Runner #"},
        {"name", "Name"},
        {"surname", "Surname"},
        {"sicard", "SiCard"},
        {"start", "Start"},
        {"arrival", "Arrival"},
        {"status", "Status"},
        {"class", "Class"},
        {"club", "Club"},
        {"format_date", "MM/dd/yyyy"},
        {"format_date_dash", "yyyy-MM-dd"},
        {"format_time", "HH:mm:ss"},
        {"format_datetime", "MM/dd/yyyy HH:mm:ss"},
        {"format_datetime_dash", "yyyy-MM-dd HH:mm:ss"},
        {"format_datetime_milli_dash", "yyyy-MM-dd HH:mm:ss.SSS"},
        {"new", "New"},
        {"delete", "Delete"},
        {"refresh", "Refresh"},
        {"save", "Save"},
        {"search", "Search"},
        {"maintenance", "Maintenance"},
        {"events", "Events"},
        {"classes", "Classes"},
        {"clubs", "Clubs"},
        {"users", "Users"},
        {"control_points", "Control Points"},
        {"key", "Key"},
        {"short", "Short"},
        {"long", "Long"},
        {"distance", "Distance"},
        {"climb", "Climb"},
        {"controls", "#Controls"},
        {"city", "City"},
        {"logo", "Logo"},
        {"administrator", "Administrator"},
        {"time_pass", "Passing time"},
        {"sequence_number", "#Sequence"},
        {"km", "Km"},
        {"relative", "#Relative"},
        {"conttrols_by_class", "Controls by category"},
        {"time_shift", "Time shift (sec.)"},
        {"general", "General"},
        {"auto", "Auto"},
        {"finish", "Finish"},
        {"clear", "Clear"},
        {"check", "Check"},
        {"type", "Type"},
        {"classic", "Classic"},
        {"normal", "Normal"},
        {"mass_start", "Mass Start"},
        {"chase_start", "Chase Start"},
        {"relays", "Relays"},
        {"relay_number", "#Relays"},
        {"teams", "Teams"},
        {"relay", "Relay"},
        {"team", "Team"},
        {"utc_offset", "UTC Offset"},
        {"replace_clubs", "Replace clubs"},
        {"battery", "Battery"},
        {"reading_time", "Reading time"},
        {"login", "Login"},
        {"gotoweb", "Goto Web"},
        {"create_event", "Create event"},
        {"plus1", "+01:00"},
        {"basic", "Basic"},
        {"extended", "Extended"},
        {"mode", "Mode"},
        {"silent", "Silent"},
        {"training", "Training"},
        {"panic_mode", "Panic mode?"},
        {"coord_analysis", "Analysis of coordinates?"},
        {"repeat_password", "Repeat password"},
        {"remote_servers", "Remote Servers"},
        {"run", "Run"},
        {"stop", "Stop"},
        {"lines", "lines"},
        {"delete_temporal_results", "Delete temporal results"},
        {"auto_download_ftp", "Automatic results download by FTP"},
        {"processed", "Processed"},
        {"process", "Process"},
        {"day_short", "d"},
        {"hour_short", "h"},
        {"minute_short", "m"},
        {"second_short", "s"},
        {"second_mid", "sec."},
        {"about", "About..."},
        {"language", "Language"},
        {"english", "English"},
        {"spanish", "Español"},
        {"src_file", "Source file"},
        {"dst_file", "Target file"},
        {"exists", "Exists"},
        {"utf", "UTF"},
        {"known_data", "Known Data"},
        {"extension", "Extension"},
        {"contents", "Contents"},
        {"results_type", "Results Type"},
        {"source", "Source"},
        {"iof_version", "IOF Version"},
        {"startlist", "Start List"},
        {"resultlist", "Result List"},
        {"totals", "Totals"},
        {"breakdown", "Breakdown"},
        {"radiocontrols", "Radiocontrols"},
        {"answers", "Answers"},
        {"finish", "Finish"},
        {"intermediate", "Intermediate"},
        {"other", "Other"},
        {"test", "Test"},
        {"test_event", "Test Event"},
        {"test_stage", "Test Stage"},
        {"score", "Score"},
        {"preo", "PreO"},
        {"tempo", "TempO"},
        {"footo", "Foot-O"},
        {"mtbo", "MTBO"},
        {"skio", "Ski-O"},
        {"trailo", "Trail-O"},
        {"raid", "Raid"},
        {"ultrascore_rogaine", "Ultrascore-Rogaine"},
        {"overall", "Overall"},
        {"stage", "Stage"},
        {"trailo_normal", "Trail-O Normal"},
        {"trailo_timed", "Trail-O Timed"},
        {"section", "Section"},
        {"check_connection", "Check Connection to Server"},
        {"btn_check", "Check"},
        {"login_event", "Login to Event"},
        {"event_id", "Event ID"},
        {"token", "Token"},
        {"idtoken", "Secret Key"},
        {"secret", "Secret"},
        {"select", "Select"},
        {"upload_data", "Upload data to server"},
        {"folder", "Folder"},
        {"waiting", "Waiting"},
        {"bytes_sent", "bytes sent"},
        {"split_class", "Split by class"},
        {"timezone_offset", "Timezone Offset"},
        {"info", "Information"},
        {"error", "Error"},
        {"confirm", "Confirmation"},
        {"csv_file_warning", "Only needed for CSV uploads"},
        {"csv_file_date", "Stage Date"},
        {"csv_file_zerotime", "Stage Zero Time"},

        {"title_main", "Welcome to OReplay Desktop Client"},
        {"title_splash", "Welcome to OReplay Desktop Client"},
        {"title_config_server", "Server configuration"},
        {"title_config_event", "Event configuration"},
        {"title_manage_runners", "Management of runners"},
        {"title_manage_teams", "Management of teams"},
        {"title_manage_events", "Management of events"},
        {"title_manage_classes", "Management of classes"},
        {"title_manage_clubs", "Management of clubs"},
        {"title_manage_users", "Management of users"},
        {"title_manage_control_points", "Management of control points"},
        {"title_manage_followup", "Management of raw follow up"},
        {"title_manage_control_class", "Management of control points by category"},
        {"title_manage_radiocontrols", "Radiocontrols Management"},
        {"title_select_class", "Category selection"},
        {"title_select_club", "Club Selection"},
        {"title_select_event", "Event Selection"},
        {"title_select_control_point", "Control Point Selection"},
        {"title_select_runner", "Runner Selection"},
        {"title_global_panel", "Global Panel"},
        {"title_login", "Log into the system"},
        {"title_create_event", "Create an event"},
        {"title_update_event", "Event Modification"},

        {"tooltip_check_connection", "Check the connection to the O-Replay server"},
        {"tooltip_event_id", "Event ID that has been created in O-Replay"},
        {"tooltip_token", "Token or security value associated with the event in O-Replay"},
        {"tooltip_idtoken", "Secret Key as it's been copied at O-Replay"},
        {"tooltip_login", "Log in to the event using the Id and Token"},
        {"tooltip_event_web", "Open a browser and access the event page on O-Replay"},
        {"tooltip_stage_list", "List of event stages. Select one"},
        {"tooltip_upload_folder", "Select a valid folder where the files to upload are located"},
        {"tooltip_extensions", "List of supported formats. Select one"},
        {"tooltip_split_cat", "Only for large files that give an upload error. Allows you to divide uploads by categories"},
        {"tooltip_start_stop", "Start or Stop the process of searching for files and automatically uploading data"},

        {"question_export_data", "Do you want to export data?"},
        {"question_sure_action_undone", "Are you sure? This action can not be undone"},
        {"question_sure", "Are you sure?"},

        {"info_enter_app", "Entering the program"},
        {"info_exit_app", "Exiting the program"},
        {"info_data_saved_ok", "Data saved Ok"},
        {"info_data_saved_error", "Error while saving data"},
        {"info_enter_code", "Enter a value for the code"},
        {"info_user_not_exists", "User/password doesn't exist"},
        {"info_not_valid_date", "It's not a valid date"},
        {"info_not_valid_time", "It's not a valid time"},
        {"info_all_values_needed", "All fields should have a value"},
        {"info_utc_not_valid", "UTC not valid +-nn:nn"},
        {"info_password_not_match", "The passwords don't match"},
        {"info_username_exists", "The username already exists"},
        {"info_event_saved_error", "Error while saving the event on the database"},
        {"info_not_valid_datetime", "It's not a valid date/time"},
        {"info_not_valid_number", "It's not a valid number"},
        {"info_app_closing", "The application is going to close"},
        {"info_no_new_contents", "No new contents found"},
        {"info_process_started", "Process Started."},
        {"info_process_finished", "Process Finished."},
        {"info_waiting_connection", "Waiting for connection."},
        {"info_connection_ok", "Connection established."},
        {"info_connection_nook", "Could not connect."},
        {"info_connection_timeout", "Timeout reached before connection."},
        {"info_connection_break", "Connection break while processing data."},
        {"info_login_ok", "Login Ok."},
        {"info_login_nook", "Login failed."},
        {"info_data_novalid", "No valid data"},
        {"info_upload_started", "Upload process started"},
        {"info_upload_stopped", "Upload process stopped"},
        {"info_fileprocess_started", "File process started"},
        {"info_fileprocess_stopped", "File process stopped"},
        {"info_fileprocess_finished", "File process finished"},
        {"info_classprocess_started", "Class process started"},
        {"info_classprocess_stopped", "Class process stopped"},
        {"info_classprocess_finished", "Class process finished"},
        {"info_new_version", "There is a newer version of the desktop client.\n Replace your release or download\nthe newest windows installer to update"},
        {"info_current_version", "Your version is up to date"},
        {"info_connection_version", "Could not connect to check for updates."},
        {"info_date_time_needed", "Formato CSV. Es necesario escribir un valor correcto de fecha y hora de comienzo de la etapa"},

        {"error_exception", "Something went wrong and the program entered an exception block"},
        {"error_format_unknown", "Format unknown or not supported yet"},
        {"error_nothing_to_do", "Nothing to do"},
        {"error_nothing_to_do_noevent", "No event info - Nothing to do"},
        {"error_nothing_to_do_nofile", "No file - Nothing to do"},
        {"error_nothing_to_do_noconf", "File inspection failed - Nothing to do"},
        {"error_not_supported", "Not supported yet"},
        {"error_not_supported_xml_contents", "XML Contents - Not supported yet"},
        {"error_not_supported_csv_contents", "CSV Contents - Not supported yet"},
        {"error_not_supported_filetype", "Type of file - Not supported yet"},
    };
protected Object[][] getContents() {
	return contents;
}
}
