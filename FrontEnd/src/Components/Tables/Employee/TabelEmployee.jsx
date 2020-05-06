import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelEmployee.module.css';
import SirioTable from '../SirioTable';
import EmployeeService from '../../../Services/EmployeeService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import SirioMessageButton from "../../Button/ActionButton/SirioMessageButton";

class TabelEmployee extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            openNotification: true
        };

        this.renderRows = this.renderRows.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    endNotification() {
        this.setState({
            openNotification: false
        })
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
            <>
                <SirioTable
                    title="Daftar Staff"
                    data={this.state.rowList}
                    id='id'
                    columnsDefinition={this.columns}
                    includeSearchBar
                    headerButton={this.getButtonsHeader()}
                />
                {this.props.location.state && this.props.location.state.addSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Employee berhasil Disimpan"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.deleteSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Employee berhasil Dihapus"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
                {this.props.location.state && this.props.location.state.editSuccess && this.state.openNotification &&
                <SirioMessageButton
                    show
                    classes="d-none"
                    modalTitle="Employee berhasil Diubah"
                    customConfirmText="Tutup"
                    onClick={this.endNotification}
                />
                }
            </>
        );
    }
}

export default withRouter(TabelEmployee);