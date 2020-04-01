import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelEmployee.module.css';
import SirioTable from '../SirioTable';
import EmployeeService from '../../../Services/EmployeeService';
import { NavLink } from 'react-router-dom';

export default class TabelEmployee extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: []
        }

        this.renderRows = this.renderRows.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    async renderRows() {
        const response = await EmployeeService.getEmployeeList();

        this.setState({
            rowList: response.data.result
        })
    }

    getButtonsFirst(cell, row) {
        return (
            <NavLink to={{
                pathname: "/employee/detail",
                state: {
                    id: row.idEmployee,
                }
            }}>
                <SirioButton
                    purple
                >
                    Detail
                </SirioButton>
            </NavLink>
        )
    }

    getButtonsHeader() {
        return (
            <NavLink to={{
                pathname: "/employee/tambah"
            }}>
                <SirioButton
                    purple
                >
                    Tambah Pengguna
                </SirioButton>
            </NavLink>
        )
    }

    columns = [
        {
            dataField: 'nama',
            text: 'NAMA',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "20%", textAlign: 'left' };
            }
        }, {
            dataField: 'jabatan',
            text: 'JABATAN',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "20%", textAlign: 'left' };
            }
        }, {
            dataField: 'username',
            text: 'USERNAME',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "15%", textAlign: 'left' };
            }
        }, {
            dataField: 'role.namaRole',
            text: 'ROLE',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "20%", textAlign: 'left' };
            }
        }, {
            dataField: 'noData 1',
            text: '',
            headerClasses: classes.colheader,
            classes: classes.rowItem,
            formatter: this.getButtonsFirst,
            style: () => {
                return { textAlign: 'center' }
            },
            headerStyle: (colum, colIndex) => {
                return { width: "15%", textAlign: 'left' };
            }
            
        }];

    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    render() {
        return (
            <SirioTable
                title="Daftar Employee"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.getButtonsHeader()}
            />
        );
    }
}