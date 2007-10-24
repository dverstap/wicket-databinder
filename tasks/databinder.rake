def child_artifact(child_spec, parent_artifact, path)
  parent_artifact.invoke
  artifact(child_spec) do |task|
    file_name = File.basename(path) 
    dest_path = File.dirname(task.name) 
    unz = Unzip.new(dest_path => parent_artifact)
    unz.from_path(File.dirname(path)).include(file_name)
    unz.extract()
    tgt = (File.join(dest_path, file_name))
    mv(tgt, task.name) if not File.exist? task.name
  end
end

def dep_preview(path, parent_spec)
  file_name = File.basename(path)
  parent_artifact = artifact(parent_spec)
  file("dep_preview/" + file_name => parent_artifact) do |task|
    dest_path = File.dirname(task.name) 
    unz = Unzip.new(dest_path => parent_artifact)
    unz.from_path(File.dirname(path)).include(file_name)
    unz.extract()
  end
end

def embed_server
  def serve(params = [])
    params << "-Dmail.smtp.host=$SMTP_HOST" if (ENV["SMTP_HOST"])
    "java $JAVA_OPTIONS " << params.join(" ") << " -classpath " << compile.target.to_s() << ":" << compile.classpath.join(":") << " net.databinder.web.DataServer"
  end

  task :run => :compile do
    if (ENV["JAVA_REBEL"]) then
      system serve(["-noverify", "-javaagent:$JAVA_REBEL", "-Xbootclasspath/a:$JAVA_REBEL"])
    else
      system serve()
    end
  end

  pid_f = _("server.pid")

  def pid() File.exist?(pid_f) && IO.read(pid_f).to_i end

  task :start => :compile do
    start_port = ENV['START_PORT'] || '8080'
    system 'nohup ' << serve(['-Djetty.port=' + start_port]) << '>/dev/null &\echo $! > ' << pid_f
    puts "started server port:" << start_port << " pid: " << pid().to_s
  end

  task :stop do
    if pid
      begin
        Process.kill("TERM", pid)
      rescue Errno::ESRCH
        puts "server not running at pid: #{pid}; removing record"
      end
      rm pid_f
    else
      puts "no server id on record"
    end
  end
end

WICKET_SELF = group("wicket", "wicket-auth-roles", "wicket-extensions", :under=>"org.apache.wicket", :version=>"1.3.0-beta4")
WICKET=[WICKET_SELF, "commons-collections:commons-collections:jar:2.1.1","commons-logging:commons-logging:jar:1.0.4"]

HB_CORE_ZIP=download(artifact("org.hibernate:hibernate:zip:3.2.5.ga")=>"http://dl.sourceforge.net/sourceforge/hibernate/hibernate-3.2.5.ga.zip")
HIBERNATE_CORE = child_artifact("org.hibernate:hibernate:jar:3.2.5.ga", HB_CORE_ZIP, "hibernate-3.2/hibernate3.jar")
HIBERNATE_SELF = [HIBERNATE_CORE,"org.hibernate:hibernate-annotations:jar:3.3.0.ga", "org.hibernate:hibernate-commons-annotations:jar:3.3.0.ga"]
JTA = child_artifact("javax.transaction:jta:jar:1.0.1B", HB_CORE_ZIP, "hibernate-3.2/lib/jta.jar")
CGLIB = child_artifact("cglib:cglib:jar:2.1_3", HB_CORE_ZIP, "hibernate-3.2/lib/cglib-2.1.3.jar")
EHCACHE=child_artifact("net.sf.ehcache:ehcache:jar:1.2.3", HB_CORE_ZIP, "hibernate-3.2/lib/ehcache-1.2.3.jar")
HIBERNATE=[HIBERNATE_SELF, JTA, EHCACHE, CGLIB, "javax.persistence:persistence-api:jar:1.0", "dom4j:dom4j:jar:1.6.1", "asm:asm-attrs:jar:1.5.3", "asm:asm:jar:1.5.3", "antlr:antlr:jar:2.7.6"]

DATABINDER_COMPONENTS="net.databinder:databinder-components:jar:1.1-SNAPSHOT"
DATABINDER_SELF=[DATABINDER_COMPONENTS, group("databinder","databinder-dispatch", "databinder-auth-components", "databinder-models", :under => "net.databinder", :version => "1.1-SNAPSHOT")]
XML_RPC = ["org.apache.ws.commons:ws-commons-util:jar:1.0.1","org.apache.xmlrpc:xmlrpc-client:jar:3.0","org.apache.xmlrpc:xmlrpc-common:jar:3.0", "commons-httpclient:commons-httpclient:jar:3.0.1", "commons-codec:commons-codec:jar:1.2"]
DATABINDER=[DATABINDER_SELF, WICKET, HIBERNATE, XML_RPC]

JETTY = "org.mortbay.jetty:jetty:jar:6.1.5"
JETTY_UTIL = "org.mortbay.jetty:jetty-util:jar:6.1.5"
