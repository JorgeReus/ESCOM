#include "mongoose.h"
static const char *s_http_port = "8000";
static struct mg_serve_http_opts s_http_server_opts;

static void ev_handler(struct mg_connection *nc, int ev, void *p) {
	s_http_server_opts.cgi_interpreter = "/usr/bin/php-cgi7.2";
	s_http_server_opts.cgi_file_pattern = NULL;
	if (ev == MG_EV_HTTP_REQUEST) {
		mg_serve_http(nc, (struct http_message *) p, s_http_server_opts);
	}
}
int main(int argc, char *argv[]) {
	if (argc > 2) {
		printf("Usage ./htmlServer port\n");
	} else if (argc == 2) {
		s_http_port = argv[1];
	}
	struct mg_mgr mgr;
	struct mg_connection *nc;
	mg_mgr_init(&mgr, NULL);
	printf("Starting web server on port %s\n", s_http_port);
	nc = mg_bind(&mgr, s_http_port, ev_handler);
	if (nc == NULL) {
		printf("Failed to create listener\n");
		return 1;
	}
	// Set up HTTP server parameters
	mg_set_protocol_http_websocket(nc);
	s_http_server_opts.document_root = "."; // Serve current directory
	s_http_server_opts.enable_directory_listing = "yes";
	for (;;) {
		mg_mgr_poll(&mgr, 1000);
	}
	mg_mgr_free(&mgr);
	return 0;
}