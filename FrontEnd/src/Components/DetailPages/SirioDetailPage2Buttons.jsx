import React, { Component } from 'react';
import BootstrapTable from "react-bootstrap-table-next";
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import classes from './SirioDetailPage.module.css';
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css"
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import SirioButton from '../Button/SirioButton';

class SirioDetailPage extends Component {

    render() {       
        return ( 
            <div>
                <h1 className={classes.title}>{this.props.title}</h1>
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
                                        //ref={n => this.node = n}
                                        bordered={ false }
                                    />
                                </div>
                            )
                        }
                    </ToolkitProvider>
                </div>
            </div>
        );
    }

};
export default SirioDetailPage;
