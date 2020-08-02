package com.pepper.weeabot.models;

public class SlackRequest {
  String token;
  String team_id;
  String team_domain;
  String channel_id;
  String channel_name;
  String user_id;
  String user_name;
  String command;
  String text;
  String response_url;

  public SlackRequest(String token, String team_id, String team_domain, String channel_id, String channel_name,
      String user_id, String user_name, String command, String text, String response_url) {
    this.token = token;
    this.team_id = team_id;
    this.team_domain = team_domain;
    this.channel_id = channel_id;
    this.channel_name = channel_name;
    this.user_id = user_id;
    this.user_name = user_name;
    this.command = command;
    this.text = text;
    this.response_url = response_url;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getTeam_id() {
    return this.team_id;
  }

  public void setTeam_id(String team_id) {
    this.team_id = team_id;
  }

  public String getTeam_domain() {
    return this.team_domain;
  }

  public void setTeam_domain(String team_domain) {
    this.team_domain = team_domain;
  }

  public String getChannel_id() {
    return this.channel_id;
  }

  public void setChannel_id(String channel_id) {
    this.channel_id = channel_id;
  }

  public String getChannel_name() {
    return this.channel_name;
  }

  public void setChannel_name(String channel_name) {
    this.channel_name = channel_name;
  }

  public String getUser_id() {
    return this.user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getUser_name() {
    return this.user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getCommand() {
    return this.command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getResponse_url() {
    return this.response_url;
  }

  public void setResponse_url(String response_url) {
    this.response_url = response_url;
  }
}