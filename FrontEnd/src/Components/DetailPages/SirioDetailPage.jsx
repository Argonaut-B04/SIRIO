import React, { Component } from 'react';
import BootstrapTable from "react-bootstrap-table-next";
import ToolkitProvider from 'react-bootstrap-table2-toolkit';
import classes from './SirioDetailPage.module.css';
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css"
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';

class SirioDetailPage extends Component {

    render() {       
        return ( 
            <div>
                <div className={classes.headerWrapper}>
                    <h2 className={classes.title}>
                        {this.props.title}
                    </h2>
                </div>
                <div className={classes.toolkitWrapper}>
                    <ToolkitProvider
                        bootstrap4
                        keyField={this.props.id}
                        data={this.props.data}
                        columns={this.props.columnsDefinition}
                    >
                        {
                            props => (
                                <div>
                                    <BootstrapTable
                                        {...props.baseProps}
                                        bordered={ false }
                                    />
                                    
                                </div>
                            )
                        }
                    </ToolkitProvider>
                    <br></br>
                    <div className="w-100 text-right">
                        {this.props.subButton}
                    </div>
                </div>
            </div>
        );
    }

};
export default SirioDetailPage;
