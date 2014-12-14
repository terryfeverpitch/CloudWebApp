package com.cloudwebapp.client.ui.basic;

import java.util.ArrayList;

import com.cloudwebapp.client.CloudWebApp;
import com.cloudwebapp.client.clouddrive.UploadFilePanel;
import com.cloudwebapp.client.ui.MainWindow;
import com.cloudwebapp.server.File;
import com.cloudwebapp.shared.FieldVerifier;
import com.cloudwebapp.shared.FileDTO;
import com.cloudwebapp.shared.FilePathStack;
import com.cloudwebapp.shared.MessageCode;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class DrivePage extends FlexTable {
	public static UploadFilePanel uploadFilePanel;	
	private Label lbl_path;
	private FileDTORow[] rows;
	private static ArrayList<Long> list = new ArrayList<Long>();
	
	public DrivePage() {
		setWidth("100%");
		uploadFilePanel = new UploadFilePanel();
		setWidget(0, 0, uploadFilePanel);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setStyleName("gwt-drivePage-bar");
		horizontalPanel.setSpacing(1);
		setWidget(1, 0, horizontalPanel);
		horizontalPanel.setWidth("100%");
		
		Button btnRefresh = new Button("Refresh");
		btnRefresh.setStyleName("gwt-drivePage-button");
		horizontalPanel.add(btnRefresh);
		
		Button btnDelete = new Button("Delete");
		btnDelete.setStyleName("gwt-drivePage-button");
		horizontalPanel.add(btnDelete);
		Button btnNewFolder = new Button("New Folder");
		horizontalPanel.add(btnNewFolder);
		btnNewFolder.setWidth("123px");
		btnNewFolder.setStyleName("gwt-loginPage-button");

		lbl_path = new Label(MainWindow.getFilePathStack().getPath());
		lbl_path.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lbl_path.setDirectionEstimator(false);

		horizontalPanel.add(lbl_path);
		lbl_path.setWidth("100%");
		horizontalPanel.setCellVerticalAlignment(lbl_path, HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setCellWidth(lbl_path, "100%");
		lbl_path.setStyleName("gwt-drivePage-label-noborder");
		btnRefresh.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MainWindow.refresh();
			}
		});
		
		btnDelete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				list.clear();
				for(int i = 1; i < rows.length; i++)
					if(rows[i].chckbx_delete.getValue())
						list.add(rows[i].file.getId());
				
				if(list.isEmpty() || list == null) {
					Window.alert("no selected item to delete.");
					return;
				}
				boolean confirm = Window.confirm("Are you sure you want to permanently delete chosen items and it's contents?");
				if(!confirm)
					return;
				CloudWebApp.fileManager.deleteFiles(list,  
						new AsyncCallback<Integer>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("failure");
					}
					@Override
					public void onSuccess(Integer result) {
						MainWindow.refresh();
					}
				});
			}
		});
		
		btnNewFolder.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(uploadFilePanel != null)
					MainWindow.dialog = new DialogBox(false);
					HorizontalPanel row;
					VerticalPanel contents;
					contents = new VerticalPanel();
					HTML message = new HTML("Create a new folder : ");
					message.setStyleName("gwt-drivePage-label-noborder");
					final TextBox textBox = new TextBox();
					textBox.setText("New Folder");
					final Button ok = new Button("new", new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							// new folder
							CloudWebApp.fileManager.newFolder(MainWindow.getLoginAccount().getUsername(), 
									textBox.getText(), MainWindow.getFilePathStack().currentPath().folderId, 
									new AsyncCallback<Integer>() {
								@Override
								public void onFailure(Throwable caught) {
								}
								@Override
								public void onSuccess(Integer result) {
									if(result == MessageCode.NEW_FOLDER_FAILURE_NAME_OVERLAP)
										Window.alert("this destination already contains a folder named " + textBox.getText());
									else {
										MainWindow.refresh();
									}
								}
							});
							
							MainWindow.dialog.hide();	
						}
				    });
					ok.setStyleName("gwt-drivePage-button");
					
				    Button back = new Button("back", new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							MainWindow.dialog.hide();	
						}
				    });
				    back.setStyleName("gwt-drivePage-button");
					
					textBox.setStyleName("gwt-drivePage-label");
					textBox.addChangeHandler(new ChangeHandler() {
						@Override
						public void onChange(ChangeEvent event) {
							TextBox tb = (TextBox) event.getSource();
							boolean check = FieldVerifier.folderNameCheck(tb.getText());
							if(check)
								tb.removeStyleName("gwt-field-error");
							else
								tb.addStyleName("gwt-field-error");
							ok.setVisible(check);
						}
					});

				    row = new HorizontalPanel();
				    row.add(back);
				    row.add(ok);
				    
				    contents.add(message);
				    contents.add(textBox);
				    contents.add(row);
				    MainWindow.dialog.setWidget(contents);
				    MainWindow.dialog.center();
			}
		});
		
		CheckBox chckbxNewCheckBox = new CheckBox("");
		setWidget(2, 0, chckbxNewCheckBox);
		chckbxNewCheckBox.setWidth("100%");
		chckbxNewCheckBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CheckBox cb = (CheckBox) event.getSource();
				boolean isChecked = cb.getValue();
				int i = 4;
				while(i < DrivePage.this.getRowCount()) {
					if(DrivePage.this.getWidget(i, 0) == null)
						break;
					else {
						CheckBox item = (CheckBox) DrivePage.this.getWidget(i++, 0);
						item.setValue(isChecked);
					}
				}
			}
		});
		
		Label lblNewLabel_4 = new Label("Edit");
		lblNewLabel_4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblNewLabel_4.setStyleName("gwt-drivePage-title");
		setWidget(2, 1, lblNewLabel_4);
		lblNewLabel_4.setWidth("100%");
		
		Label lblNewLabel_1 = new Label("Name");
		lblNewLabel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblNewLabel_1.setStyleName("gwt-drivePage-title");
		setWidget(2, 2, lblNewLabel_1);
		lblNewLabel_1.setWidth("100%");
		
		Label lblNewLabel_2 = new Label("Size(bytes)");
		lblNewLabel_2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblNewLabel_2.setStyleName("gwt-drivePage-title");
		setWidget(2, 3, lblNewLabel_2);
		lblNewLabel_2.setWidth("100%");
		
		Label lblNewLabel_3 = new Label("Update Time");
		lblNewLabel_3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		lblNewLabel_3.setStyleName("gwt-drivePage-title");
		setWidget(2, 4, lblNewLabel_3);
		lblNewLabel_3.setWidth("100%");
		getFlexCellFormatter().setColSpan(0, 0, 5);
		getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		getFlexCellFormatter().setColSpan(1, 0, 5);
		getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
		
		gointo();
	}
	
	public void gointo() {
		CloudWebApp.fileManager.getFiles(MainWindow.getLoginAccount().getUsername(), 
				MainWindow.getFilePathStack().currentPath().folderId, 
				new AsyncCallback<ArrayList<FileDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				// failure
			}
			@Override
			public void onSuccess(ArrayList<FileDTO> result) {	
				
				if(result == null)
					rows = new FileDTORow[1];
				else
					rows = new FileDTORow[result.size() + 1];
				
				rows[0] = new FileDTORow();
				setWidget(3, 2, rows[0].fileName);
				getFlexCellFormatter().setColSpan(3, 2, 4);

				if(rows.length < 2)
					return;
				int i = 1;
				for(FileDTO f : result) {
					rows[i] = new FileDTORow(f);
					setWidget(i + 3, 0, rows[i].chckbx_delete);
					setWidget(i + 3, 1, rows[i].btn_rename);
					setWidget(i + 3, 2, rows[i].fileName);
					setWidget(i + 3, 3, rows[i].fileSize);
					setWidget(i + 3, 4, rows[i].fileUpdateTime);
					i++;
				}
				
				for(int r = 2; r < DrivePage.this.getRowCount(); r++) {
					for(int c = 0; c < DrivePage.this.getCellCount(r); c++) {
						getCellFormatter().setHorizontalAlignment(r, c, HasHorizontalAlignment.ALIGN_CENTER);
						getCellFormatter().setVerticalAlignment(r, c, HasVerticalAlignment.ALIGN_MIDDLE);
					}
				}
			}
		});
	}
	
	public static class FileDTORow implements ClickHandler {
		protected CheckBox chckbx_delete = new CheckBox("");
		protected Button btn_rename= new Button("edit");
		protected Label fileName;
		protected Label fileSize;
		protected Label fileUpdateTime;
		protected FileDTO file;
		
		public FileDTORow() {
			chckbx_delete.setVisible(false);
			btn_rename.setVisible(false);
			fileName = new Label("/..");
			fileSize = null;
			fileUpdateTime = null;
			file = null;
			
			fileName.addClickHandler(new ClickHandler() {
				// go back
				@Override
				public void onClick(ClickEvent event) {
					FilePathStack.Path current = MainWindow.getFilePathStack().currentPath();
					MainWindow.getFilePathStack().pop();	
					FilePathStack.Path parent = MainWindow.getFilePathStack().currentPath();
					if(parent == null) {
						MainWindow.getFilePathStack().push(current);
					}
					MainWindow.refresh();
				}
			});
			fileName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			fileName.setStyleName("gwt-drivePage-fileItem");
		}
		
		public FileDTORow(FileDTO row_file) {
			file = row_file;
			fileName = new Label(file.getFileName());
			fileUpdateTime = new Label(file.getUpdateTime());
			btn_rename.addClickHandler(this);
			if(file.getType() == File.DIR) {
				fileSize = new Label("Folder");
				setFolderItemStyle();
				fileName.addClickHandler(new ClickHandler() {
					// go into
					@Override
					public void onClick(ClickEvent event) {
						MainWindow.getFilePathStack().push(
								new FilePathStack.Path(file.getId(), file.getFileName()));
						MainWindow.refresh();
					}
				});
			}
			else {
				fileName.addClickHandler(new ClickHandler() {
					// download
					@Override
					public void onClick(ClickEvent event) {
						String url = "/cloudwebapp/downloadServlet?blob-key=" + file.getBlobKey() + 
								"&name=" + file.getFileName();
						Window.open(url, null, null);
					}
				});
				
				fileSize = new Label("" + file.getFileSize());
				setFileItemStyle();
			}
		}
		
		private void setFolderItemStyle() {
			btn_rename.setStyleName("gwt-drivePage-button");
			fileName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			fileName.setStyleName("gwt-drivePage-folderItem");
			
			fileSize.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			fileSize.setStyleName("gwt-drivePage-title");
			fileUpdateTime.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			fileUpdateTime.setStyleName("gwt-drivePage-title");
		}
				
		private void setFileItemStyle() {
			btn_rename.setStyleName("gwt-drivePage-button");
			
			fileName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			fileName.setStyleName("gwt-drivePage-fileItem");
			
			fileSize.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			fileSize.setStyleName("gwt-drivePage-title");
			fileUpdateTime.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			fileUpdateTime.setStyleName("gwt-drivePage-title");
		}
		
		@Override
		public void onClick(ClickEvent event) {
			MainWindow.dialog = new DialogBox(false);
			HorizontalPanel row;
			VerticalPanel contents;
			contents = new VerticalPanel();
			HTML message = new HTML("Rename the chosen file ");
			message.setStyleName("gwt-drivePage-label-noborder");
			final TextBox textBox = new TextBox();
			textBox.setText(file.getFileName());
			final Button ok = new Button("ok", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// rename file
					CloudWebApp.fileManager.renameFile(file.getId(), textBox.getText(),
							new AsyncCallback<Integer>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("failure");
						}
						@Override
						public void onSuccess(Integer result) {
							if(result == MessageCode.RENAME_FILE_FAILURE_NAME_OVERLAP)
								Window.alert("this destination already contains a folder named " + textBox.getText());
							else if(result == MessageCode.RENAME_FILE_FAILURE_NOTFOUND)
								Window.alert("target not found");

							MainWindow.refresh();
						}
					});
					
					MainWindow.dialog.hide();	
				}
		    });
			ok.setStyleName("gwt-drivePage-button");
			
		    Button back = new Button("back", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					MainWindow.dialog.hide();	
				}
		    });
		    back.setStyleName("gwt-drivePage-button");
			
			textBox.setStyleName("gwt-drivePage-label");
			textBox.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					TextBox tb = (TextBox) event.getSource();
					boolean check = FieldVerifier.folderNameCheck(tb.getText());				
					if(check)
						tb.removeStyleName("gwt-field-error");
					else
						tb.addStyleName("gwt-field-error");
					ok.setVisible(check);
				}
			});

		    row = new HorizontalPanel();
		    row.add(back);
		    row.add(ok);
		    
		    contents.add(message);
		    contents.add(textBox);
		    contents.add(row);
		    MainWindow.dialog.setWidget(contents);
		    MainWindow.dialog.center();
		}
	}
}
