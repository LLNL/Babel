#!/usr/bin/env python


def validArgs(opts):
    return (opts.has_key("smtpserver") and
            opts.has_key("recipients") and
            opts.has_key("subject") and
            opts.has_key("from"))

def sendEmail(opts):
    import smtplib
    opts["commato"] = ", ".join(opts["recipients"])
    header = """To: %(commato)s
From: %(from)s
Subject: %(subject)s
Content-Type: %(mimetype)s
""" % opts
    content = header + "\n" + opts['file'].read()
    opts['file'].close()
    server = smtplib.SMTP(opts['smtpserver'])
    server.sendmail(opts['from'], opts['recipients'], content)
    server.close()

if __name__ == '__main__':
    from getopt import getopt
    from sys import argv, stdin
    mailopts={}
    mailopts['mimetype'] = 'text/plain; charset=US-ASCII'
    mailopts['file'] = stdin
    opts, recipients = getopt(argv[1:],"s:f:", ["file=", "smtp=", "subject=", "from=", "mimetype="])
    mailopts['recipients'] = recipients
    for i in opts:
        if (i[0] == '-s') or (i[0] == "--subject"):
            mailopts['subject'] = i[1]
        elif (i[0] == '-f') or (i[0] == "--from"):
            mailopts['from'] = i[1]
        elif (i[0] == '--smtp'):
            mailopts['smtpserver'] = i[1]
        elif (i[0] == '--mimetype'):
            mailopts['mimetype'] = i[1]
        elif (i[0] == '--file'):
            mailopts['file'] = open(i[1])
        else:
            print "unknown options", i[0], i[1]
    if validArgs(mailopts):
        sendEmail(mailopts)
    else:
        print "Usage: sendmail.py --subject=<subject> --from=<email> --smtp=<server>\n    [--mimetype=<type>] [--file=<filename>]"
        print mailopts
